package io.github.hemeroc.kotlinAdventOfCode

import java.io.File

fun main() {
    println(task1sub1("/input1.txt"))
    println(task1sub2("/input1.txt"))
}

private fun task1sub1(file: String) =
        lines(file)
                .map { it.toInt() }
                .map { calculateFuel(it) }
                .sum()

private fun task1sub2(file: String) =
        lines(file)
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

fun lines(file: String) = File(object {}::class.java.getResource(file).toURI()).readLines()
