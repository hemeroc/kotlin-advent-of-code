package io.github.hemeroc.kotlinAdventOfCode.year2024.day8

import io.github.hemeroc.kotlinAdventOfCode.util.readLines

private data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    operator fun times(multiply: Int) = Point(x * multiply, y * multiply)
    infix fun isOn(map: List<CharArray>) = x in map.indices && y in map[x].indices
}

fun main() {

    val data = readLines(2024, "input8.txt").map { it.toCharArray() }
    val charMap = mutableMapOf<Char, MutableList<Point>>()

    data.forEachIndexed { x, row ->
        row.forEachIndexed { y, value ->
            charMap.getOrPut(value) { mutableListOf() }.add(Point(x, y))
        }
    }

    println(solve1(data, charMap)) // 357
    println(solve2(data, charMap)) // 1266
}

private fun solve1(map: List<CharArray>, antennas: MutableMap<Char, MutableList<Point>>): Int {
    val antiNodes = mutableSetOf<Point>()
    for ((char, points) in antennas.entries) {
        if (char == '.') continue
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val diff = points[j] - points[i]
                val candidate1 = points[i] - diff
                val candidate2 = points[j] + diff
                if (candidate1 isOn map) {
                    antiNodes.add(candidate1)
                }
                if (candidate2 isOn map) {
                    antiNodes.add(candidate2)
                }
            }
        }
    }
    return antiNodes.size
}

private fun solve2(map: List<CharArray>, antennas: MutableMap<Char, MutableList<Point>>): Int {
    val antiNodes = mutableSetOf<Point>()
    for ((char, points) in antennas.entries) {
        if (char == '.') continue
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val diff = points[j] - points[i]
                var candidate1 = points[i]
                while (candidate1 isOn map) {
                    antiNodes.add(candidate1)
                    candidate1 -= diff
                }
                var candidate2 = points[j]
                while (candidate2 isOn map) {
                    antiNodes.add(candidate2)
                    candidate2 += diff
                }
            }
        }
    }
    return antiNodes.size
}
