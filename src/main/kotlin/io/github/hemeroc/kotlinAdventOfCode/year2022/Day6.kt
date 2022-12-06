package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    val data = readLines(2022, "input6.txt")[0].toList()
    measureTimeMillis {
        val buffer = mutableListOf<Char>()
        var foundSignal = false
        var foundMessage = false
        data.forEachIndexed { i, c ->
            buffer.add(c)
            if (!foundSignal && buffer.distinctLast(4)) {
                println("Signal starts at ${i + 1}")
                foundSignal = true
            }
            if (!foundMessage && buffer.distinctLast(14)) {
                println("Message starts at ${i + 1}")
                foundMessage = true
            }
            if (foundSignal && foundMessage) {
                return@measureTimeMillis
            }
        }
        println(data)
    }.also { println("Calculated in ${it}ms") }
}

private fun <E> List<E>.distinctLast(n: Int) =
    takeLast(n).distinct().size == n
