package io.github.hemeroc.kotlinAdventOfCode.year2024

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import io.github.hemeroc.kotlinAdventOfCode.year2024.DIRECTION.DOWN
import io.github.hemeroc.kotlinAdventOfCode.year2024.DIRECTION.LEFT
import io.github.hemeroc.kotlinAdventOfCode.year2024.DIRECTION.RIGHT
import io.github.hemeroc.kotlinAdventOfCode.year2024.DIRECTION.UNKNOWN
import io.github.hemeroc.kotlinAdventOfCode.year2024.DIRECTION.UP

enum class DIRECTION(val symbol: Char, val position: Position) {
    UP('^', Position(-1, 0)),
    RIGHT('>', Position(0, 1)),
    DOWN('v', Position(1, 0)),
    LEFT('<', Position(0, -1)),
    UNKNOWN('X', Position(0, 0)),
    ;
}

data class Position(val x: Int, val y: Int) {
    infix fun isOn(map: List<CharArray>) =
        x in map.indices && y in map[x].indices

    operator fun plus(other: Position) = Position(x + other.x, y + other.y)
}

fun main() {
    val map = readLines(2024, "input6.txt").map { it.toCharArray() }

    val (startPosition, startDirection) = findStartingPosition(map)
    var (position, direction) = startPosition to startDirection
    val locations = mutableSetOf<Pair<Position, DIRECTION>>()

    do {
        val loopDetected = !locations.add(position to direction)
        move(map, position, direction).also { (newPosition, newDirection) ->
            position = newPosition
            direction = newDirection
        }
    } while (position isOn map || loopDetected)
    println(locations.map { it.first }.toSet().size)

    var loops = 0;
    locations.map { it.first }.filter { it != startPosition }.toSet().forEach { (x, y) ->
            locations.clear()
            map[x][y] = '#'
            position = startPosition
            direction = startDirection
            do {
                val loopDetected = !locations.add(position to direction)
                move(map, position, direction).also { (newPosition, newDirection) ->
                    position = newPosition
                    direction = newDirection
                }
                if (loopDetected) {
                    loops++
                }
            } while (position isOn map && !loopDetected)
            map[x][y] = '.'
    }
    println(loops)
}

private operator fun List<CharArray>.get(position: Position) =
    if (position isOn this) {
        get(position.x)[position.y]
    } else {
        null
    }

fun move(map: List<CharArray>, position: Position, direction: DIRECTION): Pair<Position, DIRECTION> =
    (position + direction.position).let {
        if (map[it] == '#') {
            position
        } else {
            it
        } to rotateIfNecessary(map, it, direction)
    }

fun rotateIfNecessary(map: List<CharArray>, position: Position, direction: DIRECTION) =
    if (map[position] == '#') {
        when (direction) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            UNKNOWN -> UNKNOWN
        }
    } else {
        direction
    }

fun findStartingPosition(map: List<CharArray>): Pair<Position, DIRECTION> {
    map.forEachIndexed { x, row ->
        row.forEachIndexed { y, column ->
            DIRECTION.entries.find { it.symbol == column }?.let {
                return Position(x, y) to it
            }
        }
    }
    return Position(-1, -1) to UNKNOWN
}