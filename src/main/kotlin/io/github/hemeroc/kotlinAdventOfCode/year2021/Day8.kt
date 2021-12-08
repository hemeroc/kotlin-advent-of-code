package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val detectableValues = mapOf(1 to 2, 7 to 3, 4 to 4, 8 to 7)
        val data = readLines(2021, "input8.txt")
        val solution1 = data.sumOf { line ->
            line.split("|").last().split(" ").count { detectableValues.values.contains(it.length) }
        }
        println(solution1)
    }.also { println("Calculated in ${it}ms") }
}