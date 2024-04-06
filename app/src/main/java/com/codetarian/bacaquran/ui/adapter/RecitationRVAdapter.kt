package com.codetarian.bacaquran.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.R
import com.github.difflib.patch.DeltaType
import com.github.difflib.patch.Patch
import java.lang.StringBuilder


class RecitationRVAdapter(
    private val context: Context,
    private val patches: Patch<String>
) : RecyclerView.Adapter<RecitationRVAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTextViewDeltaType: TextView = itemView.findViewById(R.id.text_delta_type)
        val mTextViewMessage: TextView = itemView.findViewById(R.id.text_message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recitation, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = patches.deltas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val delta = patches.deltas[position]
        val deltaType: String
        val bgColor: Int
        val message = StringBuilder()
        when (delta.type) {
            DeltaType.DELETE -> {
                deltaType = context.getString(R.string.insert)
                bgColor = R.color.persian_green
                val word = delta.source.lines.joinToString("")
                message.append("Anda lupa membaca '$word' pada ayat")
            }
            DeltaType.CHANGE -> {
                deltaType = context.getString(R.string.delete)
                bgColor = R.color.cinnabar
                val word = delta.target.lines.joinToString("")
                message.append("Kata '$word' tidak terdapat pada ayat")
            }
            DeltaType.INSERT -> {
                deltaType = context.getString(R.string.delete)
                bgColor = R.color.cinnabar
                val word = delta.target.lines.joinToString("")
                message.append("Kata '$word' tidak terdapat pada ayat")
            }
            else -> return
        }
        with(holder) {
            mTextViewDeltaType.text = deltaType
            mTextViewDeltaType.setBackgroundResource(bgColor)
            mTextViewMessage.text = message
        }
    }
}