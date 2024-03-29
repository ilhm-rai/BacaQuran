package com.codetarian.bacaquran.ext

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.forEachVisibleHolder(action: (holder: RecyclerView.ViewHolder) -> Unit) {
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        val holder = findContainingViewHolder(child!!)
        if (holder != null) {
            action(holder)
        }
    }
}
