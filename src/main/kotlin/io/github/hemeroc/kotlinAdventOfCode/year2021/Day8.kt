package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import java.lang.RuntimeException
import kotlin.system.measureTimeMillis

// 2 -> 1
// 3 -> 7
// 4 -> 4
// 5 -> 2,3,5
// 6 -> 0,6,9
// 7 -> 8

fun main() {
    measureTimeMillis {
        val data = readLines(2021, "input8.txt")
        val solution1 = data.sumOf { line ->
            line.split("|").last().split(" ").count { it.length in listOf(2, 3, 4, 7) }
        }
        val solution2 = data.sumOf { line ->
            val elements = line.split(" ")
            val one = elements.first { it.length == 2 }
            val four = elements.first { it.length == 4 }
            line.split("|").last().split(" ").joinToString(separator = "") { digit ->
                when (digit.length) {
                    2 -> "1"
                    3 -> "7"
                    4 -> "4"
                    5 -> when {
                        digit.contains(one) -> "3"
                        digit.contains(four, 3) -> "5"
                        else -> "2"
                    }
                    6 -> {
                        when {
                            digit.contains(four) -> "9"
                            digit.contains(one) -> "0"
                            else -> "6"
                        }
                    }
                    7 -> "8"
                    else -> ""
                }
            }.toInt()
        }
        println(solution1)
        println(solution2) // 1010472
    }.also { println("Calculated in ${it}ms") }
}

private fun String.contains(other: String, segmentsContained: Int = other.length) =
    toList().count { other.contains(it) } == segmentsContained
