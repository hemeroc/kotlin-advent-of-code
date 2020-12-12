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
        val start = ShipPosition(Position(0, 0), E)
        val end = programInput.fold(start) { ship, (instruction, value) ->
            when (instruction) {
                'N', 'E', 'S', 'W' -> ship.copy(position = ship.position + Direction.valueOf("$instruction").position * value)
                'F' -> ship.copy(position = ship.position + ship.heading.position * value)
                'L' -> ship.copy(heading = Direction.values()[((ship.heading.ordinal - (value / 90)) % 4)
                    .let { if (it >= 0) it else 4 - abs(it) }]
                )
                'R' -> ship.copy(heading = Direction.values()[(ship.heading.ordinal + (value / 90)) % 4])
                else -> ship
            }.also { println("$instruction$value -> $it") }
        }
        println(
            """
                Manhattan distance part1: ${abs(end.position.x) + abs(end.position.y)}
                Occupied seats part2: 2
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}

data class ShipPosition(val position: Position, val heading: Direction)
enum class Direction(val position: Position) {
    N(Position(0, 1)),
    E(Position(1, 0)),
    S(Position(0, -1)),
    W(Position(-1, 0)),
}

data class InstructionHolder(val instruction: Char, val amount: Int)
