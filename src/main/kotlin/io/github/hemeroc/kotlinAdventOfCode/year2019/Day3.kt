package io.github.hemeroc.kotlinAdventOfCode.year2019

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val center = Point(0, 0)
    val lines = readLines(2019, "input3.txt").map {
        var lastPoint = center
        it.split(",").map { moveOperation ->
            when (moveOperation.head()) {
                "U" -> lastPoint.up(moveOperation.tail().toInt())
                "D" -> lastPoint.down(moveOperation.tail().toInt())
                "L" -> lastPoint.left(moveOperation.tail().toInt())
                "R" -> lastPoint.right(moveOperation.tail().toInt())
                else -> throw RuntimeException()
            }.also { newLine ->
                lastPoint = newLine.end
            }
        }
    }
    val intersections = lines[0]
            .flatMap { line -> lines[1].mapNotNull { it.intersection(line) } }
            .filter { point -> point != center }
    val manhattanDistanceToNearestIntersection = intersections.map { point -> abs(point.x) + abs(point.y) }.minOrNull()
    println(manhattanDistanceToNearestIntersection)
    val realDistanceToNearestIntersection = intersections
            .map { intersection -> lines[0].distanceToPoint(intersection) + lines[1].distanceToPoint(intersection) }
            .minOrNull()
    println(realDistanceToNearestIntersection)
}

private fun List<Line>.distanceToPoint(point: Point): Double {
    val pathSegments = takeWhile { line -> !line.contains(point) }
    return if (pathSegments.isNotEmpty()) {
        pathSegments.map { it.length }.sum() + Line(pathSegments.last().end, point).length
    } else {
        Line(Point(0, 0), point).length
    }
}

fun String.head() = this.take(1)
fun String.tail() = this.drop(1)

data class Line(
        val start: Point,
        val end: Point
) {
    private val a = end.y - start.y
    private val b = start.x - end.x
    private val c = a * start.x + b * start.y
    val length = sqrt(a.toDouble().pow(2) + b.toDouble().pow(2))
    fun intersection(line: Line): Point? {
        val determinant = a * line.b - line.a * b
        return if (determinant == 0) {
            null
        } else {
            val x = (line.b * c - b * line.c) / determinant
            val y = (a * line.c - line.a * c) / determinant
            val intersection = Point(x, y)
            if (contains(intersection) && line.contains(intersection)) intersection else null
        }
    }

    fun contains(point: Point): Boolean {
        return min(start.x, end.x) <= point.x &&
                point.x <= max(start.x, end.x) &&
                min(start.y, end.y) <= point.y &&
                point.y <= max(start.y, end.y)
    }
}

data class Point(
        val x: Int,
        val y: Int
) {
    fun up(value: Int) = Line(this, copy(y = y + value))
    fun down(value: Int) = Line(this, copy(y = y - value))
    fun right(value: Int) = Line(this, copy(x = x + value))
    fun left(value: Int) = Line(this, copy(x = x - value))
}
