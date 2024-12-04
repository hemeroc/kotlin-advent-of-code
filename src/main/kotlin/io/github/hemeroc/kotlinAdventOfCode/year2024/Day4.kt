package io.github.hemeroc.kotlinAdventOfCode.year2024

import io.github.hemeroc.kotlinAdventOfCode.util.readLines

fun main() {
    val data = readLines(2024, "input4.txt").map { it.toCharArray() }.toTypedArray()
    var sumXmas = 0
    var sumRealXmas = 0
    for (x in data.indices) {
        for (y in data[x].indices) {
            if (data[x][y] == 'X') {
                sumXmas += search(data, x, y, 1, 0, 'M') + // down
                        search(data, x, y, 0, 1, 'M') + // right
                        search(data, x, y, 1, 1, 'M') + // down-right
                        search(data, x, y, -1, 0, 'M') + // up
                        search(data, x, y, 0, -1, 'M') + // left
                        search(data, x, y, -1, -1, 'M') + // up-left
                        search(data, x, y, -1, 1, 'M') + // up-right
                        search(data, x, y, 1, -1, 'M') + // down-left
                        0
            }
            if (data[x][y] == 'A') {
                val foundXMas = search(data, x - 2, y - 2, 1, 1, 'M') +
                        search(data, x + 2, y + 2, -1, -1, 'M') +
                        search(data, x + 2, y - 2, -1, 1, 'M') +
                        search(data, x - 2, y + 2, 1, -1, 'M') +
                        0
                if (foundXMas >= 2) {
                    sumRealXmas++
                }

            }
        }
    }
    println(sumXmas)
    println(sumRealXmas)
}

fun search(data: Array<CharArray>, x: Int, y: Int, dirX: Int, dirY: Int, search: Char): Int {
    val newX = x + dirX
    val newY = y + dirY
    if (newX < 0 || newY < 0 || newX >= data.size || newY >= data[newX].size) {
        return 0
    }
    if (search != data[newX][newY]) {
        return 0
    }
    return when (search) {
        'M' -> search(data, newX, newY, dirX, dirY, 'A')
        'A' -> search(data, newX, newY, dirX, dirY, 'S')
        'S' -> 1
        else -> 0
    }
}
