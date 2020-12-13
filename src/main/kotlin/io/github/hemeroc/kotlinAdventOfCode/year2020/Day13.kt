package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.partitionIndexed
import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import io.github.hemeroc.kotlinAdventOfCode.util.rem
import java.math.BigInteger.ONE
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val programInput = readLines(2020, "input13.txt")
        val earliestDep = programInput[0].toInt()
        val busInput = programInput[1].split(',')
        val bus = busInput
            .mapNotNull { it.toIntOrNull() }
            .map { dep -> (earliestDep % dep).let { offset -> Pair(dep, if (offset != 0) dep - offset else 0) } }
            .minByOrNull { it.second } ?: throw RuntimeException()
        val firstTime = busInput
            .map { it.toBigIntegerOrNull() ?: ONE }
            .partitionIndexed({ _, value -> value }, { index, value -> (index % value).inv() + value })
            .let { (first, second) ->
                first.reduce { l, r -> l * r }.let { product ->
                    (first zip second).sumOf { (f, s) -> s * (product / f).let { it * it.modInverse(f) } } % product
                }
            }
        println(
            """
                Bus to take ${bus.first} at ${bus.second + earliestDep}, multiplication: ${bus.first * bus.second}
                First time of bus chain: $firstTime
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}
