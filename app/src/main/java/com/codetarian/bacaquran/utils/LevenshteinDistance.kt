package com.codetarian.bacaquran.utils

object LevenshteinDistance {

    fun calculate(s1: String, s2: String): Float {
        val m = s1.length
        val n = s2.length
        val dp = Array(m + 1) { FloatArray(n + 1) }

        for (i in 0..m) {
            for (j in 0..n) {
                when {
                    i == 0 -> dp[i][j] = j.toFloat()
                    j == 0 -> dp[i][j] = i.toFloat()
                    s1[i - 1] == s2[j - 1] -> dp[i][j] = dp[i - 1][j - 1]
                    else -> {
                        dp[i][j] = 1f + minOf(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1])
                    }
                }
            }
        }

        val maxLen = maxOf(m, n).toFloat()
        return dp[m][n] / maxLen
    }
}