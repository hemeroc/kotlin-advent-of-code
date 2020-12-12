package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.Position
import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import io.github.hemeroc.kotlinAdventOfCode.year2020.Direction.E
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val programInput =
            readLines(2020, "input12.txt")
                .map { InstructionHolder(it[0], it.substring(1).toInt()) }
        val ship1 = solve1(programInput, Position(0, 0))
        val ship2 = solve2(programInput, Position(0, 0))
        println(
            """
                Manhattan distance part1: ${abs(ship1.x) + abs(ship1.y)}
                Manhattan distance part2: ${abs(ship2.x) + abs(ship2.y)}
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}

private fun solve1(
    programInput: List<InstructionHolder>,
    initialShipPosition: Position
): Position {
    var shipHeading = E
    return programInput.fold(initialShipPosition) { shipPosition, (instruction, value) ->
        when (instruction) {
            'N', 'E', 'S', 'W' -> shipPosition + Direction.valueOf("$instruction").position * value
            'F' -> shipPosition + shipHeading.position * value
            'L' -> {
                shipHeading = Direction.values()[((shipHeading.ordinal - (value / 90)) % 4)
                    .let { if (it >= 0) it else 4 - abs(it) }]
                shipPosition
            }
            'R' -> {
                shipHeading = Direction.values()[(shipHeading.ordinal + (value / 90)) % 4]
                shipPosition
            }
            else -> shipPosition
        }
    }
}

private fun solve2(
    programInput: List<InstructionHolder>,
    initialShipPosition: Position
): Position {
    var shipPosition = initialShipPosition
    programInput.fold(Position(10, 1)) { waypoint, (instruction, value) ->
        when (instruction) {
            'N', 'E', 'S', 'W' -> waypoint + Direction.valueOf("$instruction").position * value
            'F' -> {
                shipPosition += waypoint * value
                waypoint
            }
            'L' -> (0 until (value / 90)).fold(waypoint) { oldPos, _ -> Position(x = oldPos.y * -1, y = oldPos.x) }
            'R' -> (0 until (value / 90)).fold(waypoint) { oldPos, _ -> Position(x = oldPos.y, y = oldPos.x * -1) }
            else -> waypoint
        }
    }
    return shipPosition
}

enum class Direction(val position: Position) {
    N(Position(0, 1)),
    E(Position(1, 0)),
    S(Position(0, -1)),
    W(Position(-1, 0)),
}

data class InstructionHolder(val instruction: Char, val amount: Int)
