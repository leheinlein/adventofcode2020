package com.projectelizabeth

import java.io.File
import java.lang.IllegalArgumentException


fun main() {
    val count = File("resources/02-input.txt")
        .readLines()
        .count { isValid(it) }
    print("There are $count valid passwords")
}

fun isValid(str: String): Boolean {
    val match = pattern.find(str) ?: throw IllegalArgumentException("invalid input: '$str'")
    val values = match.groupValues
    return isValid(values[1].toInt(), values[2].toInt(), values[3][0], values[4])
}

fun isValid(position1: Int, position2: Int, character: Char, password: String): Boolean {
    return (password[position1 - 1] == character) xor (password[position2 - 1] == character)
}

fun isValidFirst(min: Int, max: Int, character: Char, password: String): Boolean {
    var count = 0
    for (c: Char in password) {
        if (c == character) {
            count++
        }
    }
    return count in min..max
}

val pattern = """(\d+)-(\d+)\s(\w):\s([\w]+)""".toRegex()
