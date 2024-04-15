package com.codetarian.bacaquran.utils

object LevenshteinDistance {

    fun calculate(s1: String, s2: String): Double {
        val len1 = s1.length
        val len2 = s2.length
        val matrix = Array(len1 + 1) { IntArray(len2 + 1) }

        for (i in 0..len1) {
            matrix[i][0] = i
        }
        for (j in 0..len2) {
            matrix[0][j] = j
        }

        for (i in 1..len1) {
            for (j in 1..len2) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                matrix[i][j] = minOf(
                    matrix[i - 1][j] + 1,
                    matrix[i][j - 1] + 1,
                    matrix[i - 1][j - 1] + cost
                )

                if (i > 1 && j > 1 && s1[i - 1] == s2[j - 2] && s1[i - 2] == s2[j - 1]) {
                    matrix[i][j] = minOf(matrix[i][j], matrix[i - 2][j - 2] + cost)
                }
            }
        }

        val maxLen = maxOf(len1, len2)
        val distance = matrix[len1][len2]
        return ((maxLen - distance).toDouble() / maxLen) * 100
    }
}