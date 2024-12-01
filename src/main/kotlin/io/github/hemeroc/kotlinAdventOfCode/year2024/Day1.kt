package io.github.hemeroc.kotlinAdventOfCode.year2024

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.math.abs

fun main() {
    val split = Regex("\\s+")
    val (left, right) = readLines(2024, "input1.txt")
        .map { it.split(split, 2) }
        .map { (l, r) -> l.toLong() to r.toLong() }
        .unzip()
        .let { (l, r) -> l.sorted() to r.sorted() }
    left.mapIndexed { i, v -> abs(right[i] - v) }.sum().also { println(it) }
    left.sumOf { v -> v * right.count { it == v } }.also { println(it) }
}
