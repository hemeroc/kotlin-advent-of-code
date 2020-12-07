package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
//    light red bags contain 1 bright white bag, 2 muted yellow bags.
//    dark orange bags contain 3 bright white bags, 4 muted yellow bags.
//    bright white bags contain 1 shiny gold bag.
//    muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
//    shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
//    dark olive bags contain 3 faded blue bags, 4 dotted black bags.
//    vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
//    faded blue bags contain no other bags.
//    v
    measureTimeMillis {
        val bagMap = mutableMapOf<String, MutableSet<String>>()
        val bagRegex = Regex("(?<bag>\\S+ \\S+) bag")
        readLines(2020, "input7.txt").forEach { input ->
            val found = bagRegex.findAll(input)
            val outerBag = found.first().groups["bag"]?.value ?: throw RuntimeException()
            found.drop(1).forEach { innerBagMatch ->
                val innerBag = innerBagMatch.groups["bag"]?.value ?: throw RuntimeException()
                if (innerBag != "no other")
                    bagMap[innerBag] = bagMap.getOrDefault(innerBag, mutableSetOf()).also { it.add(outerBag) }
            }
        }
        println(bagMap)
        val findOuterBags = findOuterBags(bagMap, "shiny gold")
        println(findOuterBags)
        val outerBagCount = findOuterBags.size - 1
        println(
            """
                Outer bag count: $outerBagCount
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}

fun findOuterBags(bagMap: MutableMap<String, MutableSet<String>>, searchBag: String): Set<String> {
    val mutableSet = bagMap[searchBag] ?: return mutableSetOf(searchBag)
    return mutableSet.flatMap { findOuterBags(bagMap, it) }.toSet() + mutableSetOf(searchBag)
}
