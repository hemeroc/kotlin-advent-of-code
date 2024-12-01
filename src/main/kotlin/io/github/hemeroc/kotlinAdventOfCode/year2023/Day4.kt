package io.github.hemeroc.kotlinAdventOfCode.year2023

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.math.pow
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

fun main() {
    val test = """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """
    val data = readLines(2023, "input4.txt")

    test.solve1().also { println(it) }
    data.solve1().also { println(it) }
    test.solve2().also { println(it) }
    data.solve2().also { println(it) }
}

private fun String.solve1() = trimIndent().split("\n").solve1()
private fun List<String>.solve1(): TimedValue<Any> {
    val whitespaces = Regex("\\W+")
    return measureTimedValue {
        sumOf { card ->
            val split = card.split(":", "|")
            val id = split[0].substring(5).trim().toInt()
            val win = split[1].trim().split(whitespaces).map { it.toInt() }.toSet()
            val own = split[2].trim().split(whitespaces).map { it.toInt() }.toSet()
            val overlaps = (own.intersect(win).size).coerceAtLeast(0)
            (if (overlaps == 0) 0 else 2.0.pow(overlaps - 1)).toInt()
        }
    }
}

private fun String.solve2() = trimIndent().split("\n").solve2()

private fun List<String>.solve2(): TimedValue<Any> = measureTimedValue {

}