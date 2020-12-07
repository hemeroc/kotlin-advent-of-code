package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val inputLines = readLines(2020, "input7.txt")
        val startingBag = "shiny gold"
        println(
            """
                Outer bag count: ${solvePart1(inputLines, startingBag)}
                Inner bag count: ${solvePart2(inputLines, startingBag)}
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}

private fun solvePart1(inputLines: List<String>, startingBag: String): Int {
    val bagMap = mutableMapOf<String, MutableList<String>>()
    val bagRegex = Regex("(?<bag>\\S+ \\S+) bag")
    inputLines.forEach { input ->
        val found = bagRegex.findAll(input)
        val outerBag = found.first().groups["bag"]?.value ?: throw RuntimeException()
        found.drop(1).forEach { innerBagMatch ->
            val innerBag = innerBagMatch.groups["bag"]?.value ?: throw RuntimeException()
            if (innerBag != "no other") {
                bagMap[innerBag] = bagMap.getOrDefault(innerBag, mutableListOf())
                    .also { it.add(outerBag) }
            }
        }
    }
    return findOuterBags(bagMap, startingBag).size - 1
}

private fun solvePart2(inputLines: List<String>, startingBag: String): Int {
    val bagMap = mutableMapOf<String, List<Pair<Int, String>>>()
    val bagRegex = Regex("((?<count>\\d+) )?(?<bag>\\S+ \\S+) bag")
    inputLines.forEach { input ->
        val found = bagRegex.findAll(input)
        val outerBag = found.first().groups["bag"]?.value ?: throw RuntimeException()
        val toList = found.drop(1).map { innerBagMatch ->
            val innerBag = innerBagMatch.groups["bag"]?.value ?: throw RuntimeException()
            if (innerBag != "no other") {
                val innerBagCount = innerBagMatch.groups["count"]?.value?.toIntOrNull() ?: throw RuntimeException()
                Pair(innerBagCount, innerBag)
            } else null
        }.filterNotNull().toList()
        if (toList.isNotEmpty()) bagMap[outerBag] = toList

    }
    return countInnerBags(bagMap, startingBag) - 1
}

fun countInnerBags(bagMap: MutableMap<String, List<Pair<Int, String>>>, searchBag: String, times: Int = 1): Int {
    val mutableSet = bagMap[searchBag] ?: return 1 * times
    return mutableSet.map { countInnerBags(bagMap, it.second, it.first) * times }.sum() + times
}

fun findOuterBags(bagMap: MutableMap<String, MutableList<String>>, searchBag: String): Set<String> {
    val mutableSet = bagMap[searchBag] ?: return mutableSetOf(searchBag)
    return mutableSet.flatMap { findOuterBags(bagMap, it) }.toSet() + mutableSetOf(searchBag)
}
