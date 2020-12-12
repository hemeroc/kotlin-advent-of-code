package io.github.hemeroc.kotlinAdventOfCode.util

import java.io.File

fun readFile(year: Int, filename: String) =
    File(
        object {}::class.java.getResource("/io/github/hemeroc/kotlinAdventOfCode/year$year/$filename")
            .toURI()
    )

fun readLines(year: Int, filename: String) =
    readFile(year, filename)
        .readLines()

fun readCharacters(year: Int, filename: String) =
    readFile(year, filename)
        .readText()
        .toList()

data class Position(val x: Int, val y: Int) {
    operator fun plus(position: Position) = Position(x + position.x, y + position.y)
    operator fun times(value: Int) = Position(x * value, y * value)
}

fun <T> List<List<T>>.forEachPositioned(function: (Position, T) -> Unit) =
    this.forEachIndexed { x, sub ->
        sub.forEachIndexed { y, element ->
            function(Position(x, y), element)
        }
    }

operator fun <T> List<MutableList<T>>.set(current: Position, value: T) {
    this[current.x][current.y] = value
}

operator fun <T> List<List<T>>.get(current: Position) =
    this[current.x][current.y]
