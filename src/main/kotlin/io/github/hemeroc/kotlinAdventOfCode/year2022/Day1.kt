package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.split
import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val data = readLines(2022, "input1.txt")
            .split { it == "" }
            .map { element -> element.sumOf { it.toInt() } }
            .sortedDescending()
            .take(3)
        println(data.first())
        println(data.sum())
    }.also { println("Calculated in ${it}ms") }
}
