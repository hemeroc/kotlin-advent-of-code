package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val splitPattern = Regex(" ")
        var depth = 0
        var horizontalPosition = 0
        var aim = 0
        readLines(2021, "input2.txt")
            .forEach {
                val (direction, amountString) = it.split(splitPattern, 2)
                val amount = amountString.toInt()
                when (direction) {
                    "up" -> aim -= amount
                    "down" -> aim += amount
                    "forward" -> {
                        horizontalPosition += amount
                        depth += aim * amount
                    }
                }
            }
        println(
            """
            Solution1: ${aim * horizontalPosition}
            Solution1: ${depth * horizontalPosition}
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}
