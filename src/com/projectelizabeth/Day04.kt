package com.projectelizabeth

import java.io.File

fun main() {
    var validPassports = 0
    var currentBitSet = 0
    var currentLine = ""
    File("resources/04-input.txt")
        .readLines()
        .forEach line@{ line ->
            if (line.isEmpty()) {
                if (isValidPassport(currentBitSet)) {
                    validPassports++
                }
                currentBitSet = 0
                currentLine = ""
            } else {
                line.split(" ")
                    .forEach { pair ->
                        currentLine += " $pair"
                        val tokens = pair.split(":")
                        if (tokens.size != 2) {
                            throw IllegalArgumentException("Invalid: '$line' -> '$pair'")
                        }
                        val fieldName = tokens[0]
                        val value = tokens[1]
                        if(isValid(fieldName, value)) {
                            val position = fieldNameToPosition[fieldName]
                                ?: throw IllegalStateException("Missing field: '$fieldName'")
                            currentBitSet = currentBitSet or position
                        } else {
                            return@line
                        }
                    }
            }
        }
    if (isValidPassport(currentBitSet)) {
        validPassports++
    }
    println("Valid passports: $validPassports")
}

fun isValid(key: String, value: String) =
    when(key) {
        "byr" -> value.toInt() in 1920..2002
        "iyr" -> value.toInt() in 2010..2020
        "eyr" -> value.toInt() in 2020..2030
        "hgt" -> isValidHeight(value)
        "hcl" -> hairPattern.matches(value)
        "ecl" -> validEyColors.contains(value)
        "pid" -> pidPattern.matches(value)
        "cid" -> true
        else -> throw IllegalArgumentException("invalid key: $key")
    }


fun isValidPassport(currentBitSet: Int) =
    currentBitSet and MATCHING_BITSET == MATCHING_BITSET

fun isValidHeight(value: String): Boolean {
    val matches = heightPattern.find(value)?.groupValues
    if(matches.isNullOrEmpty()) {
        return false
    }
    val num = matches[1].toInt()
    val unit = matches[2]
    return when (unit) {
        "cm" -> num in 150..193
        "in" -> num in 59..76
        else -> throw IllegalArgumentException("invalid unit for hgt: $value")
    }
}

const val MATCHING_BITSET = 127
val heightPattern = """(\d+)(cm|in)""".toRegex()
val hairPattern = """#[0-9a-f]{6}""".toRegex()
val pidPattern = """[0-9]{9}""".toRegex()

var fieldNameToPosition: Map<String, Int> = mapOf(
    "byr" to (1 shl 0),
    "iyr" to (1 shl 1),
    "eyr" to (1 shl 2),
    "hgt" to (1 shl 3),
    "hcl" to (1 shl 4),
    "ecl" to (1 shl 5),
    "pid" to (1 shl 6),
    "cid" to (1 shl 7)
)

val validEyColors = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")