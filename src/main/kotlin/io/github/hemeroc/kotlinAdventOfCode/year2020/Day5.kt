package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val replaceWithZero = Regex("[FL]")
        val replaceWithOne = Regex("[BR]")
        val rowRegex = Regex("(?<row>[01]{7})(?<column>[01]{3})")
        val result = readLines(2020, "input5.txt")
        val seats = result.map { line ->
            val groups = rowRegex.matchEntire(
                line
                    .replace(replaceWithOne, "1")
                    .replace(replaceWithZero, "0")
            )?.groups ?: throw IllegalArgumentException()
            val row = groups["row"]?.value?.toIntOrNull(2) ?: throw IllegalArgumentException()
            val column = groups["column"]?.value?.toIntOrNull(2) ?: throw IllegalArgumentException()
            val seat = row * 8 + column
            seat
        }.sorted()
        val maxSeat = seats.maxOrNull()
        val missingSeat = seats.findIndexed { index, seat ->
            index != 0 && seats[index - 1] != seat - 1
        }?.let { it - 1 }
        println(
            """
                Max seat: $maxSeat
                Your seat: $missingSeat
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}

private fun <E> List<E>.findIndexed(predicate: (Int, E) -> Boolean): E? {
    forEachIndexed { index, element ->
        if (predicate.invoke(index, element)) return element
    }
    return null
}
