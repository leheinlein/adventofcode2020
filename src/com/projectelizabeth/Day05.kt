package com.projectelizabeth

import java.io.File
import kotlin.math.pow


fun main() {
    val allIds = File("resources/05-input.txt")
        .readLines()
        .map { calculateSeatNumber(it) }
        .toList()

    val id = findMissingId(allIds)
    println("My seat number: $id")
}

fun findMissingId(allIds: List<Int>): Int {
    if (allIds.size < 2) {
        throw java.lang.IllegalArgumentException("Not enough rows")
    }
    val sortedIds = allIds.sorted()
    sortedIds.forEachIndexed { index, id ->
        if (sortedIds[index+1] != id+1) {
            return id+1
        }
    }
    throw IllegalStateException("Couldn't find a missing seat")
}

fun calculateSeatNumber(input: String): Int {
    val row = calculateBinarySearch(input, 7, 0, BACK, FRONT)
    val column = calculateBinarySearch(input, 3, 7, RIGHT, LEFT)
    return (row * 8) + column
}

private fun calculateBinarySearch(input: String, numChars: Int, start: Int, upperChar: Char, lowerChar: Char): Int {
    var min = 0
    var max = numChars
    var charPosition = start
    for (i in (numChars - 1) downTo 0) {
        val c = input[charPosition]
        val shift = 2.0.pow(i).toInt()
        when (c) {
            upperChar -> min += shift
            lowerChar -> max -= shift
            else -> throw IllegalArgumentException("Unexpected character: '$c'")
        }
        charPosition++
    }
    return min
}

const val FRONT = 'F'
const val BACK = 'B'
const val RIGHT = 'R'
const val LEFT = 'L'

