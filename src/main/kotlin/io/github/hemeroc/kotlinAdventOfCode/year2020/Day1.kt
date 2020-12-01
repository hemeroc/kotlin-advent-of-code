package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines

private val Pair<Int, Int>.product: Int
    get() = first * second

private val Triple<Int, Int, Int>.product: Int
    get() = first * second * third

fun main() {
    val datapoints = readLines(2020, "input1.txt").map { it.toInt() }
    println("""
        Found: ${solvePart1(datapoints)?.product}
        Found: ${solvePart2(datapoints)?.product}
    """.trimIndent())
}

private fun solvePart1(datapoints: List<Int>): Pair<Int, Int>? {
    datapoints.forEachIndexed { index, first ->
        (index until datapoints.size).forEach {
            datapoints[it].let { second ->
                if (first + second == 2020) return Pair(first, second)
            }
        }
    }
    return null
}

private fun solvePart2(datapoints: List<Int>): Triple<Int, Int, Int>? {
    datapoints.forEach { first ->
        datapoints.forEach { second ->
            datapoints.forEach { third ->
                if (first + second + third == 2020) return Triple(first, second, third)
            }
        }
    }
    return null
}
