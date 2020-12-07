package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val outerBagCount = part1()
        val innerBagCount = part2()
        println(
            """
                Outer bag count: $outerBagCount
                Inner bag count: $innerBagCount
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}

private fun part1(): Int {
    val bagMap = mutableMapOf<String, MutableList<String>>()
    val bagRegex = Regex("(?<bag>\\S+ \\S+) bag")
    readLines(2020, "input7.txt").forEach { input ->
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
    return findOuterBags(bagMap, "shiny gold").size - 1
}

private fun part2(): Int {
    val bagMap = mutableMapOf<String, List<Pair<Int, String>>>()
    val bagRegex = Regex("((?<count>\\d+) )?(?<bag>\\S+ \\S+) bag")
    readLines(2020, "input7.txt").forEach { input ->
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
    println(bagMap)
    return countInnerBags(bagMap, "shiny gold") - 1
}

fun countInnerBags(bagMap: MutableMap<String, List<Pair<Int, String>>>, searchBag: String, times: Int = 1): Int {
    val mutableSet = bagMap[searchBag] ?: return 1 * times
    return mutableSet.map { subBag ->
        countInnerBags(bagMap, subBag.second, subBag.first) * times
    }.sum() + times
}

fun findOuterBags(bagMap: MutableMap<String, MutableList<String>>, searchBag: String): Set<String> {
    val mutableSet = bagMap[searchBag] ?: return mutableSetOf(searchBag)
    return mutableSet.flatMap { findOuterBags(bagMap, it) }.toSet() + mutableSetOf(searchBag)
}
