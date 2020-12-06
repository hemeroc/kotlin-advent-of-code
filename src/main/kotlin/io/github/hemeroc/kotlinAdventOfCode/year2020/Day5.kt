package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val seats = readLines(2020, "input5.txt").map { line ->
            val row = line.take(7)
                .replace('F', '0')
                .replace('B', '1')
                .toIntOrNull(2)
                ?: throw IllegalArgumentException()
            val column = line.takeLast(3)
                .replace('L', '0')
                .replace('R', '1')
                .toIntOrNull(2)
                ?: throw IllegalArgumentException()
            val seat = row * 8 + column
            seat
        }.sorted()
        val maxSeat = seats.maxOrNull()
        val missingSeat = ((seats.first()..seats.last()) - seats).single()
        println(
            """
                Max seat: $maxSeat
                Your seat: $missingSeat
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}