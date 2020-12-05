package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.streams.toList
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val inputParser = Regex("^(?<first>\\d+)-(?<second>\\d+) (?<letter>\\S): (?<password>\\S+)$")
        var part1Matches = 0
        var part2Matches = 0
        readLines(2020, "input2.txt").map { input ->
            val groups = inputParser.matchEntire(input)?.groups ?: return@map false
            val first = groups["first"]?.value?.toInt() ?: return@map false
            val second = groups["second"]?.value?.toInt() ?: return@map false
            val letter = groups["letter"]?.value?.single() ?: return@map false
            val password = groups["password"]?.value ?: return@map false
            part1Matches += if (part1Matches(password, letter, first, second)) 1 else 0
            part2Matches += if (part2Matches(password, letter, first, second)) 1 else 0
        }
        println(
            """
                Valid part1 passwords: $part1Matches
                Valid part2 passwords: $part2Matches
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}

fun part1Matches(password: String, letter: Char, from: Int, to: Int): Boolean =
    password.chars().toList().groupingBy { it }.eachCount()[letter.toInt()]?.let {
        it in from..to
    } ?: (from == 0 && to == 0)

fun part2Matches(password: String, letter: Char, first: Int, second: Int): Boolean =
    (password[first - 1] == letter) xor (password[second - 1] == letter)
