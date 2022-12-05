package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    val instructions =
        readLines(2022, "input5.txt").drop(10).map { it.drop(5).split(" from ", " to ").map { it.toInt() } }
    measureTimeMillis {
        with(towers()) {
            instructions.forEach { (times, from, to) ->
                get(to - 1).addAll(get(from - 1).removeLast(times))
            }
            println(map { it.last() }.joinToString(""))
        }
        with(towers()) {
            instructions.forEach { (times, from, to) ->
                get(to - 1).addAll(get(from - 1).removeLast(times, true))
            }
            println(map { it.last() }.joinToString(""))
        }
    }.also { println("Calculated in ${it}ms") }
}

private fun towers(): List<ArrayDeque<Char>> =
    listOf(
        ArrayDeque("HRBDZFLS".toList()),
        ArrayDeque("TBMZR".toList()),
        ArrayDeque("ZLCHNS".toList()),
        ArrayDeque("SCFJ".toList()),
        ArrayDeque("PGHWRZB".toList()),
        ArrayDeque("VJZGDNMT".toList()),
        ArrayDeque("GLNWFSPQ".toList()),
        ArrayDeque("MZR".toList()),
        ArrayDeque("MCLGVRT".toList()),
    )

private fun <E> ArrayDeque<E>.removeLast(times: Int, reversed: Boolean = false): Collection<E> =
    (1..times).map { removeLast() }.let {
        if (reversed) it.reversed() else it
    }
