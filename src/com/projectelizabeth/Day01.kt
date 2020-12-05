package com.projectelizabeth

import java.io.File


fun main() {
    val numbers = File("resources/01-input.txt")
        .readLines()
        .map { it.toInt() }
    for (i in numbers.indices) {
        val iVal = numbers[i]
        for (j in i + 1 until numbers.size) {
            val jVal = numbers[j]
            for (k in i+2 until numbers.size) {
                val kVal = numbers[k]
                if (iVal + jVal + kVal == 2020) {
                    print("$iVal x $jVal + $kVal = ${iVal * jVal * kVal}")
                }
            }

        }
    }
}

