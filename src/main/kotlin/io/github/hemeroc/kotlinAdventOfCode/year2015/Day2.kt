package io.github.hemeroc.kotlinAdventOfCode.year2015

import io.github.hemeroc.kotlinAdventOfCode.util.readLines

fun main() {
    val sortedMeasures = readLines(2015, "input2.txt").map { line ->
        line.split("x")
                .map { it.toInt() }
                .sorted()
    }
    val paperNeeded = sortedMeasures.map { (small, middle, large) ->
        2 * large * middle + 2 * middle * small + 2 * small * large + (small * middle)
    }.sum()
    val ribbonNeeded = sortedMeasures.map { (small, middle, large) ->
        2 * small + 2 * middle + small * middle * large
    }.sum()
    println("""
        Square feet of paper needed: $paperNeeded
        Feed of ribbon needed: $ribbonNeeded
    """.trimIndent())
}
