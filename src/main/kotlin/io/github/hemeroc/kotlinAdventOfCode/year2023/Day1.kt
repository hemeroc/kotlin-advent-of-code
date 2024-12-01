package io.github.hemeroc.kotlinAdventOfCode.year2023

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureNanoTime

fun main() {

    val regex = Regex("(?=(?<digit>[1-9]|one|two|three|four|five|six|seven|eight|nine))")
    val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        .mapIndexed { index, string -> string to index + 1 }.toMap()
    val data = readLines(2023, "input1.txt")
    measureNanoTime {
        data.sumOf { line ->
            regex.findAll(line).let { match -> digit(numbers, match.first()) * 10 + digit(numbers, match.last()) }
        }.also { println(it) }
    }.also { println("Calculated in ${it / 1000}µs") }

}

fun digit(numbers: Map<String, Int>, possibleDigit: MatchResult): Int = with(possibleDigit.groups["digit"]?.value) {
    numbers[this] ?: this?.toInt() ?: throw RuntimeException("$this does not match a digit")
}
