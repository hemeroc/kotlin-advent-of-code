package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis


fun main() {
    var start = Point(0,0)
    var goal = Point(0,0)
    val data = readLines(2022, "input12.txt").mapIndexed { y, line ->
        line.mapIndexed { x, char ->
            when (char) {
                'S' -> {
                    start = Point(x, y)
                    'a'
                }

                'E' -> {
                    goal = Point(x, y)
                    'z'
                }

                else -> char
            }
        }
    }.toTypedArray()
    println(start)
    println(goal)
    measureTimeMillis {
        println(data)
    }.also { println("Calculated in ${it}ms") }
}

data class Point(
    val x: Int,
    val y: Int
)