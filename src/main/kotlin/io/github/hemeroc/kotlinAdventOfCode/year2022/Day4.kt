package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    val data = readLines(2022, "input4.txt")
    measureTimeMillis {
        data.count { line ->
            line.split(",", "-").let {
                val leftFrom = it[0].toInt()
                val leftTo = it[1].toInt()
                val rightFrom = it[2].toInt()
                val rightTo = it[3].toInt()
                (leftFrom >= rightFrom && leftTo <= rightTo) || (rightFrom >= leftFrom && rightTo <= leftTo)
            }
        }.also {
            println(it)
        }
    }.also { println("Calculated in ${it}ms") }
    measureTimeMillis {
        data.count { line ->
            line.split(",", "-").let {
                val leftFrom = it[0].toInt()
                val leftTo = it[1].toInt()
                val rightFrom = it[2].toInt()
                val rightTo = it[3].toInt()
                (leftFrom in rightFrom..rightTo) ||
                (rightFrom in leftFrom..leftTo) ||
                (leftTo in rightFrom..rightTo) ||
                (rightTo in leftFrom..leftTo)
            }
        }.also {
            println(it)
        }
    }.also { println("Calculated in ${it}ms") }
}
