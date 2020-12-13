package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import java.math.BigInteger
import java.math.BigInteger.ONE
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val programInput = readLines(2020, "input13.txt")
        val earliestDeparture = programInput[0].toInt()
        val busToTake = programInput[1]
            .split(',')
            .mapNotNull { it.toIntOrNull() }
            .map {
                val departureMinutesAgo = earliestDeparture % it
                if (departureMinutesAgo != 0) Pair(it, it - departureMinutesAgo) else Pair(it, 0)
            }
            .minByOrNull { it.second } ?: throw RuntimeException()
        val crt = crt(programInput[1].split(','))
        println(
            """
                Bus to take ${busToTake.first} at ${busToTake.second + earliestDeparture}, multiplication: ${busToTake.first * busToTake.second}
                CRT: $crt
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}

fun crt(busses: List<String>): BigInteger {
    // https://www.youtube.com/watch?v=zIFehsBHB8o
    val n = mutableListOf<BigInteger>()
    val a = mutableListOf<BigInteger>()
    val prod = busses.mapIndexed() { index, value ->
        (value.toBigIntegerOrNull() ?: ONE).also {
            n.add(it)
            a.add(-1 * index % it + it)
        }
    }.reduce { l, r -> l * r }
    return (n zip a).map { (n, a) -> a * (prod / n) * (prod / n).modInverse(n) }.sumOf { it } % prod
}

private operator fun BigInteger.minus(value: Int): BigInteger = this - value.toBigInteger()
private operator fun Int.rem(other: BigInteger): BigInteger = this.toBigInteger() % other
