package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    val data = readLines(2022, "input8.txt").map { it.toCharArray().map(Char::digitToInt) }.toTypedArray()
    measureTimeMillis {
        val visibleTrees = (data.indices).sumOf { row ->
            data[row].indices.count { col ->
                data.isVisible(row, col)
            }
        }
        val maxScenicScore = (data.indices).maxOf { row ->
            data[row].indices.maxOf { col ->
                data.scenicScore(row, col)
            }
        }

        println(visibleTrees)
        println(maxScenicScore)
    }.also { println("Calculated in ${it}ms") }
}

private fun Array<List<Int>>.scenicScore(row: Int, col: Int): Int {
    val height = this[row][col]
    return scenicScore(height, row + 1, col, 1, 0) *
            scenicScore(height, row - 1, col, -1, 0) *
            scenicScore(height, row, col + 1, 0, 1) *
            scenicScore(height, row, col - 1, 0, -1)
}

private fun Array<List<Int>>.scenicScore(height: Int, row: Int, col: Int, rowMut: Int, colMut: Int): Int {
    return if (row < 0 || col < 0 || row >= size || col >= this[row].size) {
        0
    } else if (this[row][col] >= height) {
        1
    } else {
        1 + scenicScore(height, row + rowMut, col + colMut, rowMut, colMut)
    }
}


private fun Array<List<Int>>.isVisible(row: Int, col: Int): Boolean {
    val height = this[row][col]
    return isVisible(height, row + 1, col, 1, 0) ||
            isVisible(height, row - 1, col, -1, 0) ||
            isVisible(height, row, col + 1, 0, 1) ||
            isVisible(height, row, col - 1, 0, -1)
}

private fun Array<List<Int>>.isVisible(height: Int, row: Int, col: Int, rowMut: Int, colMut: Int): Boolean {
    return if (row < 0 || col < 0 || row >= size || col >= this[row].size) {
        true
    } else if (this[row][col] >= height) {
        false
    } else {
        isVisible(height, row + rowMut, col + colMut, rowMut, colMut)
    }
}

