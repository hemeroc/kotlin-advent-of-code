package io.github.hemeroc.kotlinAdventOfCode.year2024.day3

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureNanoTime

private const val RIGHT = "right"
private const val LEFT = "left"
private const val DO = "do"
private const val DONT = "don"

fun main() {
    val regex = Regex("mul\\((?<$LEFT>\\d{1,3}),(?<$RIGHT>\\d{1,3})\\)|(?<$DO>do)\\(\\)|(?<$DONT>don)'t\\(\\)")
    val data = readLines(2024, "input3.txt")
        .flatMap { regex.findAll(it) }.toList()
    measureNanoTime {
        data.sumOf {
            if (it.groups[LEFT] != null) {
                it.groups[LEFT]!!.value.toLong() * it.groups[RIGHT]!!.value.toLong()
            } else {
                0L
            }
        }.also { println("Part 1: $it ") }
    }.also { println("Calculated in ${it / 1000}µs") }
    measureNanoTime {
        var enabled = true
        data.sumOf {
            when {
                it.groups[DO] != null -> {
                    enabled = true; 0L
                }

                it.groups[DONT] != null -> {
                    enabled = false; 0L
                }

                enabled -> it.groups[LEFT]!!.value.toLong() * it.groups[RIGHT]!!.value.toLong()
                else -> 0L
            }
        }.also { println("Part 2: $it ") }
    }.also { println("Calculated in ${it / 1000}µs") }
}
