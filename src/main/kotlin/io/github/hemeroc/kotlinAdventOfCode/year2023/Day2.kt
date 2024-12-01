package io.github.hemeroc.kotlinAdventOfCode.year2023

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.math.max
import kotlin.system.measureNanoTime

fun main() {
    measureNanoTime {
        val bag = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14,
        )
        readLines(2023, "input2.txt").map {
            val split = it.split(":")
            val game = split[0].substring(5).toInt()
            val rounds = split[1].split(";")
            rounds.forEach { round ->
                round.split(",").forEach { take ->
                    val set = take.trim().split(" ")
                    val amount = set[0].toInt()
                    val color = set[1]
                    if ((bag[color] ?: 0) < amount) {
                        return@map 0
                    }
                }
            }
            return@map game
        }.sum().also { println(it) }

        readLines(2023, "input2.txt").map {
            val split = it.split(":")
            val rounds = split[1].split(";")
            val maxBag = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0,
            )
            rounds.forEach { round ->
                round.split(",").forEach { take ->
                    val set = take.trim().split(" ")
                    val amount = set[0].toInt()
                    val color = set[1]
                    maxBag[color] = max(maxBag[color] ?: 0, amount)
                }
            }
            return@map maxBag.values.reduce { l, r -> l * r }
        }.sum().also { println(it) }

    }.also { println("Calculated in ${it / 1000}µs") }
}