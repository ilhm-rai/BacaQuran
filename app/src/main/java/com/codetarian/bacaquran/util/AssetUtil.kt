package com.codetarian.bacaquran.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object AssetUtil {

    fun copyAssets(context: Context, files: Array<String> = arrayOf()) {
        val assetManager = context.assets
        for (filename in files) {
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                inputStream = assetManager.open(filename)
                val outFile = File("${context.getExternalFilesDir(null)}", filename)
                if (outFile.exists()) {
                    return
                }
                outputStream = FileOutputStream(outFile)

                copyFile(inputStream, outputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                inputStream?.close()
                outputStream?.flush()
                outputStream?.close()
            }
        }
    }

    private fun copyFile(inputStream: InputStream, outputStream: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (inputStream.read(buffer).also { read = it } != -1) {
            outputStream.write(buffer, 0, read)
        }
    }
}