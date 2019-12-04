package io.github.hemeroc.kotlinAdventOfCode

import io.github.hemeroc.kotlinAdventOfCode.util.lines

fun main() {
    println(fuel("/input1.txt"))
    println(totalFuel("/input1.txt"))
}

private fun fuel(filename: String) =
        lines(filename)
                .map { it.toInt() }
                .map { calculateFuel(it) }
                .sum()

private fun totalFuel(filename: String) =
        lines(filename)
                .map { it.toInt() }
                .map { calculateTotalFuel(it) }
                .sum()

private fun calculateFuel(it: Int): Int {
    val fuel = it / 3 - 2
    return if (fuel > 0) fuel else 0
}

private tailrec fun calculateTotalFuel(mass: Int, fuelTotal: Int = 0): Int {
    if (mass == 0) return fuelTotal
    val fuelNeeded = calculateFuel(mass)
    return calculateTotalFuel(fuelNeeded, fuelTotal + fuelNeeded)
}
