package io.github.hemeroc.kotlinAdventOfCode.year2015

import io.github.hemeroc.kotlinAdventOfCode.util.readCharacters

fun main() {
    val floorOperations = readCharacters(2015, "input1.txt")
    var currentFloor = 0
    var firstBasementVisit = 0
    floorOperations.forEachIndexed { index, operation ->
        when (operation) {
            '(' -> currentFloor++
            ')' -> currentFloor--
        }
        if (basementNotVisited(firstBasementVisit) && inBasement(currentFloor)) {
            firstBasementVisit = index + 1
        }
    }
    println("""
        Number of floors: $currentFloor
        First basement visit: $firstBasementVisit
    """.trimIndent())
}

private fun basementNotVisited(firstBasementVisit: Int) = firstBasementVisit == 0
private fun inBasement(currentFloor: Int) = currentFloor < 0