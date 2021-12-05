package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import io.github.hemeroc.kotlinAdventOfCode.year2019.Point
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val data = readLines(2021, "input5.txt").map { it ->
            val values = Regex("(\\d+)").findAll(it).map { match -> match.value.toInt() }.toList()
            Point(values[0], values[1]) to Point(values[2], values[3])
        }
        val size = data.flatMap { it.toList().flatMap { point -> listOf(point.x, point.y) } }.maxOf { it } + 1
        val grid = Array(size) { IntArray(size) { 0 } }
        data.forEach { grid.drawStraightSegment(it) }
        println(grid.flatMap { it.toList() }.count { it > 1 })
        data.forEach { grid.drawDiagonalSegment(it) }
        println(grid.flatMap { it.toList() }.count { it > 1 })
    }.also { println("Calculated in ${it}ms") }
}

private fun Array<IntArray>.drawStraightSegment(segment: Pair<Point, Point>) {
    if (segment.first.x == segment.second.x)
        range(segment.first.y, segment.second.y).forEach { y -> this[y][segment.first.x]++ }
    else if (segment.first.y == segment.second.y)
        range(segment.first.x, segment.second.x).forEach { x -> this[segment.first.y][x]++ }
}

private fun Array<IntArray>.drawDiagonalSegment(segment: Pair<Point, Point>) {
    if (segment.first.x != segment.second.x && segment.first.y != segment.second.y) {
        val adjx = if (segment.first.x < segment.second.x) 1 else -1
        val adjy = if (segment.first.y < segment.second.y) 1 else -1
        var x = segment.first.x
        var y = segment.first.y
        range(segment.first.x, segment.second.x).forEach { _ ->
            this[y][x]++
            x += adjx
            y += adjy
        }
    }
}

fun range(start: Int, end: Int) = if (start > end) end..start else start..end
