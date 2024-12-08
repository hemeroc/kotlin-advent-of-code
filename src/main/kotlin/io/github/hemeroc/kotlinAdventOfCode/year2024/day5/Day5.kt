package io.github.hemeroc.kotlinAdventOfCode.year2024.day5

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureNanoTime

fun main() {
    val rules = mutableListOf<Pair<Long, Long>>()
    val pagesList = readLines(2024, "input5.txt").mapNotNull {
        val ruleSplit = it.split("|")
        if (ruleSplit.size == 2) {
            rules.add(ruleSplit[0].toLong() to ruleSplit[1].toLong())
            null
        } else {
            if (it.isNotEmpty()) {
                it.split(",").map { pageNumber -> pageNumber.toLong() }
            } else {
                null
            }
        }
    }.filter { it.isNotEmpty() }
    measureNanoTime {
        pagesList.sumOf { pages ->
            if (checkRules(pages, rules)) {
                pages[pages.size / 2]
            } else {
                0
            }
        }.also { print("Part 1: $it ") }
    }.also { println("Calculated in ${it / 1000}µs") }
    measureNanoTime {
        pagesList.sumOf { pages ->
            if (!checkRules(pages, rules)) {
                fixPages(pages, rules).let { it[it.size / 2] }
            } else {
                0
            }
        }.also { print("Part 2: $it ") }
    }.also { println("Calculated in ${it / 1000}µs") }
}

fun checkRules(pages: List<Long>, rules: List<Pair<Long, Long>>): Boolean =
    rules.all { (left, right) ->
        val indexOfLeft = pages.indexOf(left)
        val indexOfRight = pages.indexOf(right)
        ((indexOfLeft < 0 || indexOfRight < 0) || (indexOfLeft < indexOfRight))
    }

fun fixPages(pages: List<Long>, rules: MutableList<Pair<Long, Long>>): List<Long> {
    val fixedPages = pages.toMutableList()
    rules.forEach { (left, right) ->
        val indexOfLeft = fixedPages.indexOf(left)
        val indexOfRight = fixedPages.indexOf(right)
        if (indexOfLeft >= 0 && indexOfRight >= 0 && indexOfLeft > indexOfRight) {
            fixedPages[indexOfLeft] = right
            fixedPages[indexOfRight] = left
        }
    }
    return if (checkRules(fixedPages, rules)) {
        fixedPages
    } else {
        fixPages(fixedPages, rules)
    }
}
