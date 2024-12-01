package io.github.hemeroc.kotlinAdventOfCode.year2023

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

fun main() {
    val test = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598.. 
    """
    val data = readLines(2023, "input3.txt")

    test.solve1().also { println(it) }
    data.solve1().also { println(it) }
    test.solve2().also { println(it) }
    data.solve2().also { println(it) }
}

private fun String.solve1() = trimIndent().split("\n").solve1()

private fun List<String>.solve1(): TimedValue<Any> = measureTimedValue {
    val matrix = map { s -> s.toCharArray() }.toTypedArray()
    var sum = 0
    matrix.forEachIndexed { rowNumber, row ->
        var number = ""
        var hasAdjacentSymbol = false
        row.forEachIndexed { columnNumber, column ->
            if (column.isDigit()) {
                if (!hasAdjacentSymbol) {
                    hasAdjacentSymbol = checkAdjacentSymbol(matrix, rowNumber, columnNumber)
                }
                number += column
            } else {
                if (hasAdjacentSymbol && number.isNotEmpty()) {
                    sum += number.toInt()
                }
                hasAdjacentSymbol = false
                number = ""
            }
        }
        if (hasAdjacentSymbol && number.isNotEmpty()) {
            sum += number.toInt()
        }
    }
    sum
}

fun checkAdjacentSymbol(matrix: Array<CharArray>, rowNumber: Int, columnNumber: Int): Boolean {
    (rowNumber - 1..rowNumber + 1).forEach { row ->
        (columnNumber - 1..columnNumber + 1).forEach { col ->
            matrix.getOrNull(row)?.getOrNull(col)?.let { if (!it.isDigit() && it != '.') return true }
        }
    }
    return false
}

private fun String.solve2() = trimIndent().split("\n").solve2()

private fun List<String>.solve2(): TimedValue<Any> = measureTimedValue {
    val matrix = map { s -> s.toCharArray() }.toTypedArray()
    var sum = 0
    matrix.forEachIndexed { rowNumber, row ->
        row.forEachIndexed { columnNumber, column ->
            if (column == '*') {
                val numbers = findAdjacentNumbers(matrix, rowNumber, columnNumber)
                if (numbers.size > 2) {
                    throw RuntimeException("more than 2 at $rowNumber:$columnNumber")
                } else if (numbers.size == 2) {
                    sum += numbers.reduce { l, r -> l * r }
                }
            }
        }
    }
    sum
}

val number = Regex("[0-9]+")
fun findAdjacentNumbers(matrix: Array<CharArray>, rowNumber: Int, columnNumber: Int): MutableList<Int> {
    val numbers = mutableListOf<Int>()
    if (columnNumber != 0) {
        if (matrix[rowNumber][columnNumber - 1].isDigit()) {
            val left = String(matrix[rowNumber]).substring(0, columnNumber)
            numbers += number.findAll(left).last().value.toInt()
        }
    }
    if (columnNumber != matrix[0].size - 1) {
        if (matrix[rowNumber][columnNumber + 1].isDigit()) {
            val right = String(matrix[rowNumber]).substring(columnNumber + 1)
            numbers += number.findAll(right).first().value.toInt()
        }
    }
    if (rowNumber != 0) {
        if (matrix[rowNumber - 1][columnNumber].isDigit()) {
            var left = ""
            var right = ""
            if (columnNumber != 0) {
                if (matrix[rowNumber - 1][columnNumber - 1].isDigit()) {
                    left = String(matrix[rowNumber - 1]).substring(0, columnNumber)
                    left = number.findAll(left).last().value
                }
            }
            if (columnNumber != matrix[0].size - 1) {
                if (matrix[rowNumber - 1][columnNumber + 1].isDigit()) {
                    right = String(matrix[rowNumber - 1]).substring(columnNumber + 1)
                    right = number.findAll(right).first().value
                }
            }
            numbers += (left + matrix[rowNumber - 1][columnNumber] + right).toInt()
        } else {
            if (columnNumber != 0) {
                if (matrix[rowNumber - 1][columnNumber - 1].isDigit()) {
                    val left = String(matrix[rowNumber - 1]).substring(0, columnNumber)
                    numbers += number.findAll(left).last().value.toInt()
                }
            }
            if (columnNumber != matrix[0].size - 1) {
                if (matrix[rowNumber - 1][columnNumber + 1].isDigit()) {
                    val right = String(matrix[rowNumber - 1]).substring(columnNumber + 1)
                    numbers += number.findAll(right).first().value.toInt()
                }
            }
        }
    }
    if (rowNumber != matrix.size - 1) {
        if (matrix[rowNumber + 1][columnNumber].isDigit()) {
            var left = ""
            var right = ""
            if (columnNumber != 0) {
                if (matrix[rowNumber + 1][columnNumber - 1].isDigit()) {
                    left = String(matrix[rowNumber + 1]).substring(0, columnNumber)
                    left = number.findAll(left).last().value
                }
            }
            if (columnNumber != matrix[0].size - 1) {
                if (matrix[rowNumber + 1][columnNumber + 1].isDigit()) {
                    right = String(matrix[rowNumber + 1]).substring(columnNumber + 1)
                    right = number.findAll(right).first().value
                }
            }
            numbers += (left + matrix[rowNumber + 1][columnNumber] + right).toInt()
        } else {
            if (columnNumber != 0) {
                if (matrix[rowNumber + 1][columnNumber - 1].isDigit()) {
                    val left = String(matrix[rowNumber + 1]).substring(0, columnNumber)
                    numbers += number.findAll(left).last().value.toInt()
                }
            }
            if (columnNumber != matrix[0].size - 1) {
                if (matrix[rowNumber + 1][columnNumber + 1].isDigit()) {
                    val right = String(matrix[rowNumber + 1]).substring(columnNumber + 1)
                    numbers += number.findAll(right).first().value.toInt()
                }
            }
        }
    }
    return numbers
}