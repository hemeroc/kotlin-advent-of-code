package io.github.hemeroc.kotlinAdventOfCode.year2024.day8

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.time.measureTime

private data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun times(multiply: Int) = Point(x * multiply, y * multiply)
    infix fun isOn(map: List<CharArray>) = x in map.indices && y in map[x].indices
}

fun main() {

    val data = readLines(2024, "input8.txt").map { it.toCharArray() }
    val antennas = buildMap<Char, MutableList<Point>> {
        data.forEachIndexed { x, row ->
            row.forEachIndexed { y, value ->
                if (value != '.') {
                    getOrPut(value) { mutableListOf() }.add(Point(x, y))
                }
            }
        }
    }
    measureTime {
        solve1(data, antennas).also {
            print("Part 1: $it ")
        }
    }.also { println("Calculated in $it") }
    measureTime {
        solve2(data, antennas).also {
            print("Part 2: $it ")
        }
    }.also { println("Calculated in $it") }
}

private fun solve1(map: List<CharArray>, antennas: Map<Char, List<Point>>): Int {
    val antiNodes = mutableSetOf<Point>()
    for (locations in antennas.values) {
        for (i in locations.indices) {
            for (j in i + 1 until locations.size) {
                (locations[j] - locations[i]).let { distance ->
                    (locations[i] - distance).let {
                        if (it isOn map) {
                            antiNodes.add(it)
                        }
                    }
                    (locations[j] + distance).let {
                        if (it isOn map) {
                            antiNodes.add(it)
                        }
                    }
                }
            }
        }
    }
    return antiNodes.size
}

private fun solve2(map: List<CharArray>, antennas: Map<Char, List<Point>>): Int {
    val antiNodes = mutableSetOf<Point>()
    for (locations in antennas.values) {
        for (i in locations.indices) {
            for (j in i + 1 until locations.size) {
                (locations[j] - locations[i]).let { distance ->
                    var candidate1 = locations[i]
                    while (candidate1 isOn map) {
                        antiNodes.add(candidate1)
                        candidate1 -= distance
                    }
                    var candidate2 = locations[j]
                    while (candidate2 isOn map) {
                        antiNodes.add(candidate2)
                        candidate2 += distance
                    }
                }

            }
        }
    }
    return antiNodes.size
}
