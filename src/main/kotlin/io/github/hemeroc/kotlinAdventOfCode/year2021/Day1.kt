package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val datapoints = readLines(2021, "input1.txt").map { it.toInt() }
        println(
            """
            Number of increases1: ${datapoints.countIncreases()}
            Number of increases2: ${datapoints.slidingWindow(3).countIncreases()}
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}

private fun List<Int>.countIncreases() =
    zipWithNext { a, b -> a < b }.count { it }

private fun List<Int>.slidingWindow(size: Int) =
    windowed(size) { it.sum() }
