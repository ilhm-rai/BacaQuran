package com.codetarian.bacaquran.activity

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.R
import com.codetarian.bacaquran.adapter.RecitationRVAdapter
import com.codetarian.bacaquran.constant.ModelConstants
import com.codetarian.bacaquran.databinding.ActivityRecitationBinding
import com.codetarian.bacaquran.domain.Verse
import com.codetarian.bacaquran.util.LevenshteinDistance
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.difflib.DiffUtils
import org.deepspeech.libdeepspeech.DeepSpeechModel
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

class RecitationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecitationBinding

    private var model: DeepSpeechModel? = null

    private var transcription: String = ""
    private var transcriptionThread: Thread? = null
    private var isRecording: AtomicBoolean = AtomicBoolean(false)

    private lateinit var verse: Verse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecitationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        verse = getExtraVerse()

        setupToolbar()
        setupVerseLayout()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupToolbar() {
        val surahName = intent.getStringExtra(EXTRA_SURAH_NAME)
        binding.toolbar.title = surahName
    }

    private fun setupVerseLayout() {
        binding.verseBinding.apply {
            textAyah.text = verse.ayah.toString()
            textArabic.text = verse.arabicIndopak
            textLatin.text = verse.latin
            textTranslation.text = verse.translation
        }
    }

    private fun setupRecyclerView(originalText: String, modifiedText: String) {
        val patches = DiffUtils.diff(originalText.split(""), modifiedText.split(""))
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recitationRVAdapter by lazy { RecitationRVAdapter(this, patches) }

        binding.recyclerview.apply {
            adapter = recitationRVAdapter
            layoutManager = linearLayoutManager
            isNestedScrollingEnabled = false
        }
        ViewCompat.setNestedScrollingEnabled(binding.recyclerview, false)
    }

    private fun getExtraVerse(): Verse {
        return if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_VERSE, Verse::class.java)!!
        } else {
            val mapper = ObjectMapper()
            val jsonVerse = intent.getStringExtra(EXTRA_VERSE)
            mapper.readValue(jsonVerse, object : TypeReference<Verse>() {})
        }
    }

    private fun showReciteValidation() {
        setupRecyclerView(verse.arabic, transcription)
        binding.textTranscription.text = highlightDifferences(verse.arabic, transcription)
        val txtDistance =
            "Distance: ${LevenshteinDistance.calculate(verse.arabic, transcription)}"
        binding.textLevenshteinDistance.text = txtDistance
    }

    private fun highlightDifferences(
        originalText: String,
        modifiedText: String
    ): SpannableString {
        val patches = DiffUtils.diff(originalText.split(""), modifiedText.split(""))

        val spannableString = SpannableString(modifiedText)

        for (delta in patches.deltas) {
            val startIndex = maxOf(delta.target.position - 1, 0)
            val endIndex = minOf(
                startIndex + delta.target.lines.joinToString("").length,
                spannableString.length
            )
            val color = getColor(R.color.cinnabar)

            val start = minOf(startIndex, endIndex)
            val end = maxOf(startIndex, endIndex)

            spannableString.setSpan(
                ForegroundColorSpan(color),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableString
    }

    private fun requestPermission() {
        val permission = Manifest.permission.RECORD_AUDIO
        ActivityCompat.requestPermissions(this, arrayOf(permission), PERMISSION_CODE)
    }

    private fun checkPermission(): Boolean {
        val permission = Manifest.permission.RECORD_AUDIO
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun transcribe() {
        // We read from the recorder in chunks of 6144 shorts. With a model that expects its input
        // at 16000Hz, this corresponds to 2048/16000 = 0.384s or 384ms.
        val audioBufferSize = 6144
        val audioData = ShortArray(audioBufferSize)

        runOnUiThread {
            binding.buttonRecord.setImageResource(R.drawable.ic_action_check)
        }

        model?.let { model ->
            val streamContext = model.createStream()

            val recorder = if (checkPermission()) AudioRecord(
                MediaRecorder.AudioSource.VOICE_RECOGNITION,
                model.sampleRate(),
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                audioBufferSize
            ) else {
                return
            }
            recorder.startRecording()

            while (isRecording.get()) {
                recorder.read(audioData, 0, audioBufferSize)
                model.feedAudioContent(streamContext, audioData, audioData.size)
                val decoded = model.intermediateDecode(streamContext)
                runOnUiThread { transcription = decoded }
            }

            val decoded = model.finishStream(streamContext)

            runOnUiThread {
                binding.buttonRecord.setImageResource(R.drawable.ic_action_recording)
                transcription = decoded
            }

            recorder.stop()
            recorder.release()
        }
    }

    private fun createModel(): Boolean {
        val modelsPath = getExternalFilesDir(null).toString()
        val tfliteModelPath = "$modelsPath/${ModelConstants.TFLITE_MODEL_FILENAME}"
        val scorerPath = "$modelsPath/${ModelConstants.SCORER_FILENAME}"

        for (path in listOf(tfliteModelPath, scorerPath)) {
            if (!File(path).exists()) {
                Log.e("DeepSpeech", "Model creation failed: $path does not exist.\n")
                return false
            }
        }

        model = DeepSpeechModel(tfliteModelPath)
        model?.enableExternalScorer(scorerPath)

        return true
    }

    private fun startListening() {
        if (isRecording.compareAndSet(false, true)) {
            transcriptionThread = Thread({ transcribe() }, "Transcription Thread")
            transcriptionThread?.start()
        }
    }

    private fun stopListening() {
        isRecording.set(false)
    }

    fun onRecordClick(v: View?) {
        if (model == null) {
            if (!createModel()) {
                return
            }
            Log.i("DeepSpeech", "Created model.\n")
        }

        if (isRecording.get()) {
            stopListening()
            showReciteValidation()
        } else {
            if (checkPermission()) {
                startListening()
            } else {
                requestPermission()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (model != null) {
            model?.freeModel()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startListening()
            }
        }
    }

    companion object {
        const val EXTRA_VERSE = "extra_verse"
        const val EXTRA_SURAH_NAME = "extra_surah_name"
        private const val PERMISSION_CODE = 123
    }
}