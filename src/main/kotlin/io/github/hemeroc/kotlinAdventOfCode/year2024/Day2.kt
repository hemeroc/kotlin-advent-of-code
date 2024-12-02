package io.github.hemeroc.kotlinAdventOfCode.year2024

import io.github.hemeroc.kotlinAdventOfCode.util.readLines

fun main() {
    val data = readLines(2024, "input2.txt")
        .map { line ->
            line
                .split(" ")
                .map { it.toLong() }
        }
    data.count { !hasBadLevel(it) }
        .also {
            println(it)
        }
    data.count {
        if (hasBadLevel(it)) {
            for (i in it.indices) {
                if (!hasBadLevel(it.toMutableList().apply { removeAt(i) })) {
                    return@count true
                }
            }
            false
        } else {
            true
        }
    }.also {
        println(it)
    }
}

fun hasBadLevel(data: List<Long>): Boolean {
    var checkIncreasing: Boolean? = null
    data.forEachIndexed { i, v ->
        if (i < data.size - 1) {
            val isIncreasing = when (v - data[i + 1]) {
                in 1..3 -> false
                in -3..-1 -> true
                else -> return true
            }
            if (checkIncreasing == null) {
                checkIncreasing = isIncreasing
            } else if (checkIncreasing != isIncreasing) {
                return true
            }
        }
    }
    return false
}