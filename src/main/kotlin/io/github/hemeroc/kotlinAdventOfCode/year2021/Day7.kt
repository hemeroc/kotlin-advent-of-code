package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        with(readLines(2021, "input7.txt").single().split(",").map { it.toInt() }) {
            (minOf { it }..maxOf { it })
                .map { to -> sumOf { from -> abs(from - to) } to sumOf { from -> (1..abs(from - to)).sum() } }
                .let { consumption -> consumption.minOf { it.first } to consumption.minOf { it.second } }
                .also { println("Solution 1: ${it.first}\nSolution 2: ${it.second}") }
        }
    }.also { println("Calculated in ${it}ms") }
}
