package com.projectelizabeth

import java.io.File

fun main() {
    val a: Long = testCombo(1, 1)
    val b: Long = testCombo(3, 1)
    val c: Long = testCombo(5, 1)
    val d: Long = testCombo(7, 1)
    val e: Long = testCombo(1, 2)
    val total: Long = a*b*c*d*e
    println("$a * $b * $c * $d * $e = $total")
}

fun testCombo(right: Int, down: Int): Long {
    var treeCount: Long = 0
    var position = 0
    File("resources/03-input.txt")
        .readLines()
        .forEachIndexed { index, str ->
            if (index != 0) {
              if (index % down == 0) {
                    if (isTree(position, str)) {
                        treeCount++
                    }
                  position += right
              }

            } else {
                position += right
            }
        }
    return treeCount
}

fun isTree(position: Int, input: String): Boolean {
    return input[position % input.length] == '#'
}

fun firstMain() {
    var position = 0
    var count = 0
    File("resources/03-input.txt")
        .readLines()
        .forEachIndexed { index, str ->
            if (index != 0) {
                if (isTree(position, str)) {
                    count++
                }
            }
            position += 3
        }

    println("You would hit $count trees")
}