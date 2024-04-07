package com.codetarian.bacaquran.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtil {

    fun formatDateRelativeToNow(dateString: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = sdf.parse(dateString)
        val currentTime = Calendar.getInstance().time
        date ?: return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(currentTime)
        val diff = (currentTime.time - date.time) / 1000 // difference in seconds

        return when {
            diff < 60 -> "Baru saja"
            diff < 3600 -> "${diff / 60} menit yang lalu"
            diff < 86400 -> "${diff / 3600} jam yang lalu"
            diff < 604800 -> "${diff / 86400} hari yang lalu"
            else -> SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
        }
    }
}