package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val data = readLines(2021, "input6.txt")
            .flatMap { line -> line.split(",").map { value -> value.toInt() } }
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }
            .toMutableMap()
        println(data)
        repeat(256) {
            val dying = data[0] ?: 0
            (0..7).forEach { day ->
                data[day] = data[day + 1] ?: 0
            }
            data[6] = (data[6] ?: 0) + dying
            data[8] = dying
        }
        println(data.values.sum())
    }.also { println("Calculated in ${it}ms") }
}
