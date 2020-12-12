package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val programInput = readLines(2020, "input11.txt").map { it.toList() }.toList()
        val changes = (-1..1).flatMap { x -> (-1..1).mapNotNull { y -> iff(x != 0 || y != 0, Position(x, y)) } }
        val occupiedSeats1 = programInput.evolve(changes, 4, 1).sumBy { line -> line.count { it == '#' } }
        val occupiedSeats2 = programInput.evolve(changes, 5).sumBy { line -> line.count { it == '#' } }
        println(
            """
                Occupied seats part1: $occupiedSeats1
                Occupied seats part2: $occupiedSeats2
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}

private fun List<List<Char>>.evolve(
    changes: List<Position>,
    maxNeighbours: Int,
    maxTravelDistance: Int = Int.MAX_VALUE
): List<List<Char>> {
    var evolve = this
    do {
        val oldProgramInput = evolve
        evolve = oldProgramInput.map { line -> line.map { char -> char }.toMutableList() }
        oldProgramInput.forEachPositioned { current, seat ->
            evolve[current] =
                if (seat == 'L' && !oldProgramInput.adjacent(changes, current, maxTravelDistance).contains('#')
                ) '#'
                else if (seat == '#' && oldProgramInput.adjacent(changes, current, maxTravelDistance)
                        .count { it == '#' } >= maxNeighbours
                ) 'L'
                else evolve[current]
        }
    } while (evolve != oldProgramInput)
    return evolve
}

private fun List<List<Char>>.adjacent(changes: List<Position>, current: Position, maxTravelDistance: Int): List<Char> =
    changes.mapNotNull { change -> firstSeatInDirection(current, change, maxTravelDistance) }

private fun List<List<Char>>.firstSeatInDirection(
    current: Position,
    change: Position,
    maxTravelDistance: Int,
    travelDistance: Int = 0
): Char? {
    if (travelDistance >= maxTravelDistance) return null
    val new = current + change
    if (new.x < 0 || new.y < 0 || new.x >= size || new.y >= this[new.x].size) return null
    val value = this[new]
    return if (value == '.') firstSeatInDirection(new, change, maxTravelDistance, travelDistance + 1)
    else value
}

private fun <T> iff(boolean: Boolean, trueValue: T, falseValue: T? = null) = if (boolean) trueValue else falseValue

data class Position(val x: Int, val y: Int) {
    operator fun plus(position: Position) = Position(x + position.x, y + position.y)
}

private fun <T> List<List<T>>.forEachPositioned(function: (Position, T) -> Unit) =
    this.forEachIndexed { x, sub ->
        sub.forEachIndexed { y, element ->
            function(Position(x, y), element)
        }
    }

private operator fun <T> List<MutableList<T>>.set(current: Position, value: T) {
    this[current.x][current.y] = value
}

private operator fun <T> List<List<T>>.get(current: Position) =
    this[current.x][current.y]
