package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import java.lang.Integer.max
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val programInput = readLines(2020, "input10.txt")
            .map { it.toInt() }
            .sorted()
            .let { listOf(0) + it + (it.maxOrNull()!! + 3) }
        val offsetCounts = programInput
            .zip(programInput.drop(1))
            .map { (left, right) -> right - left }
            .groupingBy { it }
            .eachCount()
        val possiblePaths = (listOf(1L) + List(programInput.last()) { 0 }).toMutableList().also {
            programInput.drop(1).forEach { index -> it[index] = it.subList(max(index - 3, 0), index).sum() }
        }
        println(
            """
                Jolt difference calculated: ${(offsetCounts[1] ?: 0) * (offsetCounts[3] ?: 0)}
                Possible paths: ${possiblePaths.last()}
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}
