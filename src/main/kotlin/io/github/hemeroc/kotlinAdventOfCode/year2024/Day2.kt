package io.github.hemeroc.kotlinAdventOfCode.year2024

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureNanoTime

fun main() {
    val data = readLines(2024, "input2.txt")
        .map { line ->
            line
                .split(" ")
                .map { it.toLong() }
        }
    measureNanoTime {
        data.count { !hasBadLevel(it) }
            .also {
                print("Part 1: $it ")
            }
    }.also { println("Calculated in ${it / 1000}µs") }
    measureNanoTime {
        data.count {
            !(hasBadLevel(it) && it.indices.all { i ->
                hasBadLevel(
                    it.toMutableList().apply { removeAt(i) })
            })
            }
            .also {
                print("Part 2: $it ")
            }
    }.also { println("Calculated in ${it / 1000}µs") }
}

fun hasBadLevel(data: List<Long>): Boolean {
    val increasing: Boolean = data[0] < data[1]
    val allowedDecreaseWindow = 1..3
    val allowedIncreaseWindow = -3..-1
    data.forEachIndexed { i, v ->
        if (i < data.size - 1) {
            when (v - data[i + 1]) {
                in allowedDecreaseWindow -> !increasing
                in allowedIncreaseWindow -> increasing
                else -> false
            }.let { if (!it) return true }
        }
    }
    return false
}