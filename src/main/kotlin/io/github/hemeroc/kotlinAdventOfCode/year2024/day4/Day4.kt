package io.github.hemeroc.kotlinAdventOfCode.year2024.day4

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import io.github.hemeroc.kotlinAdventOfCode.year2019.tail
import io.github.hemeroc.kotlinAdventOfCode.year2024.day4.Direction.DOWN_LEFT
import io.github.hemeroc.kotlinAdventOfCode.year2024.day4.Direction.DOWN_RIGHT
import io.github.hemeroc.kotlinAdventOfCode.year2024.day4.Direction.UP_LEFT
import io.github.hemeroc.kotlinAdventOfCode.year2024.day4.Direction.UP_RIGHT
import kotlin.system.measureNanoTime

private const val SEARCHTERM = "MAS"
private val DIAGONALS = setOf(DOWN_RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT)

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun times(multiply: Int) = Point(x * multiply, y * multiply)
}

enum class Direction(private val offset: Point) {
    DOWN(Point(1, 0)),
    RIGHT(Point(0, 1)),
    DOWN_RIGHT(Point(1, 1)),
    UP(Point(-1, 0)),
    LEFT(Point(0, -1)),
    UP_LEFT(Point(-1, -1)),
    UP_RIGHT(Point(-1, 1)),
    DOWN_LEFT(Point(1, -1)),
    ;

    operator fun plus(point: Point) = offset + point
    operator fun times(multiply: Int) = offset * multiply
}

private operator fun List<CharArray>.get(point: Point) =
    if (point.x !in indices || point.y !in this[point.x].indices) {
        null
    } else {
        this[point.x][point.y]
    }

fun main() {
    val data = readLines(2024, "input4.txt").map { it.toCharArray() }
    measureNanoTime {
        data.indices.sumOf { x ->
            data[x].indices.sumOf { y ->
                Point(x, y).let { point ->
                    if (data[point] != 'X') {
                        0
                    } else {
                        Direction.entries.sumOf { search(data, point, it, SEARCHTERM) }
                    }
                }
            }
        }.also { print("Part 1: $it ") }
    }.also { println("Calculated in ${it / 1000}µs") }
    measureNanoTime {
        data.indices.sumOf { x ->
            data[x].indices.count { y ->
                Point(x, y).let { point ->
                    data[point] == 'A' && DIAGONALS.sumOf { search(data, point + it * -2, it, SEARCHTERM) } >= 2
                }
            }
        }.also { print("Part 2: $it ") }
    }.also { println("Calculated in ${it / 1000}µs") }
}

tailrec fun search(data: List<CharArray>, point: Point, direction: Direction, searchterm: String): Int {
    if (searchterm.isEmpty()) {
        return 1
    }
    val newPosition = direction + point
    if (data[newPosition] != searchterm.first()) {
        return 0
    }
    return search(data, newPosition, direction, searchterm.tail())
}
