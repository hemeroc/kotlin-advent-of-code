package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        var anyoneAnsweredWithYes = 0
        var everyoneAnsweredWithYes = 0
        val charSet = mutableSetOf<Char>()
        var charSetAll = setOf<Char>()
        (readLines(2020, "input6.txt") + "").forEach { input ->
            if (input.isBlank()) {
                anyoneAnsweredWithYes += charSet.size
                everyoneAnsweredWithYes += charSetAll.size
                charSet.clear()
                charSetAll = setOf()
            } else {
                val inputSet = input.toSet()
                charSetAll = if (charSet.isEmpty()) charSetAll + inputSet else charSetAll.intersect(inputSet)
                charSet += inputSet
            }
        }
        println(
            """
                Question anyone answered with yes: $anyoneAnsweredWithYes
                Question everyone answered with yes: $everyoneAnsweredWithYes
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}
