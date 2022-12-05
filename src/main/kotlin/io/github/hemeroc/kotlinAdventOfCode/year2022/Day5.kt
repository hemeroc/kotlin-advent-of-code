package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    val data = readLines(2022, "input5.txt")
    val instructions =
        data.dropWhile { !it.startsWith("move") }
            .map { instructionLine ->
                instructionLine
                    .split(" ")
                    .let { Triple(it[1].toInt(), it[3].toInt() - 1, it[5].toInt() - 1) }
            }
    measureTimeMillis {
        println(towers(data).applyInstructions(instructions, true).map { it.last() }.joinToString(""))
        println(towers(data).applyInstructions(instructions, false).map { it.last() }.joinToString(""))
    }.also { println("Calculated in ${it}ms") }
}

private fun List<ArrayDeque<Char>>.applyInstructions(instructions: List<Triple<Int, Int, Int>>, reversed: Boolean) =
    instructions.forEach { (times, from, to) ->
        get(to).addAll(get(from).removeLast(times, reversed))
    }.let { this }

private fun towers(data: List<String>): List<ArrayDeque<Char>> {
    val stackData = data.takeWhile { it.isNotEmpty() }
    val stackCount = stackData.last().split(" ").last().toInt()
    return (0 until stackCount).map { index ->
        val position = 1 + (index * 4)
        ArrayDeque(
            stackData
                .mapNotNull { it.getOrNull(position) }
                .filter { it in ('A'..'Z') }
                .toList()
        )
    }
}

private fun <E> ArrayDeque<E>.removeLast(times: Int, reversed: Boolean = false): Collection<E> =
    (1..times).map { removeLast() }.let {
        if (reversed) it.reversed() else it
    }
