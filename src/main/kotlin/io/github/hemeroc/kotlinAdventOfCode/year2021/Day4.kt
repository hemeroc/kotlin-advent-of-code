package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val data = readLines(2021, "input4.txt")
        val numbersDrawn = data.take(1).single().split(",").map { it.toInt() }
        var boards = data.drop(1).readBoards()
        var winningBoards: List<Board> = emptyList()

        numbersDrawn.first { number ->
            boards.firstOrNull() { board ->
                board.markNumber(number)
                board.isWinning()
            }?.let { winningBoards = listOf(it) } != null
        }.let { println(it * winningBoards.single().getUnmarked().sum()) }

        boards = data.drop(1).readBoards().toMutableList()
        numbersDrawn.first { number ->
            val foundBoards = boards.filter { board ->
                board.markNumber(number)
                board.isWinning()
            }
            boards.removeAll(foundBoards)
            winningBoards = foundBoards
            boards.isEmpty()
        }.let { println(it * winningBoards.single().getUnmarked().sum()) }

    }.also { println("Calculated in ${it}ms") }
}

private fun List<String>.readBoards(): List<Board> =
    windowed(6, 6) {
        Board(it.drop(1).map { string ->
            string
                .trim()
                .split(Regex("\\s+"))
                .map { number -> number.toIntOrNull() }
                .toTypedArray()
        }.toTypedArray())
    }

class Board(
    private val rawBoard: Array<Array<Int?>>
) {
    fun markNumber(number: Int) =
        rawBoard.forEachIndexed { x, row ->
            row.forEachIndexed { y, value ->
                if (value == number) rawBoard[x][y] = null
            }
        }

    fun isWinning(): Boolean = rowIsWinning() || columnIsWinning()

    private fun columnIsWinning() =
        rawBoard.indices.any { index ->
            rawBoard.map { row -> row[index] }.toTypedArray().all { element -> element == null }
        }

    private fun rowIsWinning() =
        rawBoard.any { row -> row.all { element -> element == null } }

    override fun toString() =
        rawBoard.joinToString("\n") { row -> row.joinToString { it.toString() } }

    fun getUnmarked() =
        rawBoard.flatten().filterNotNull()
}

