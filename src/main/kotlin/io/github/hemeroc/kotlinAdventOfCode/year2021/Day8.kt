package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val linedata = readLines(2021, "input8.txt")
            .map { input ->
                input.split("|")
                    .last()
                    .trim()
                    .split(" ")
                    .map { code -> code to digits(code.length) }

            }
            .flatten()
            .count { it.second != 0 }
        println(linedata)
    }.also { println("Calculated in ${it}ms") }
}

private fun digits(segmentCount: Int) = when (segmentCount) {
    2 -> 1
    4 -> 4
    3 -> 7
    7 -> 8
    else -> 0
}