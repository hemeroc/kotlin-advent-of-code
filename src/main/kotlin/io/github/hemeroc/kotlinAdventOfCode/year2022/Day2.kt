package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import io.github.hemeroc.kotlinAdventOfCode.year2022.Hand.*
import io.github.hemeroc.kotlinAdventOfCode.year2022.Outcome.*
import kotlin.system.measureTimeMillis

fun main() {
    val data = readLines(2022, "input2.txt")
    measureTimeMillis {
        val datapoints = data.map { line ->
            line.split(Regex(" "), 2).let { charList ->
                Pair(toHand(charList[0]), toHand(charList[1]))
            }
        }
        println(datapoints.sumOf { it.second.score(it.first).value + it.second.value })
    }.also { println("Calculated in ${it}ms") }
    measureTimeMillis {
        val datapoints = data.map { line ->
            line.split(Regex(" "), 2).let { charList ->
                Pair(toHand(charList[0]), toOutcome(charList[1]))
            }
        }
        println(datapoints.sumOf {
            val myHand = it.second.toDesiredHand(it.first)
            myHand.score(it.first).value + myHand.value})
    }.also { println("Calculated in ${it}ms") }
}

private fun toHand(it: String) = when (it) {
    "A" -> ROCK
    "B" -> PAPER
    "C" -> SCISSORS
    "X" -> ROCK
    "Y" -> PAPER
    "Z" -> SCISSORS
    else -> throw Exception()
}

private fun toOutcome(it: String) = when (it) {
    "X" -> LOSE
    "Y" -> DRAW
    "Z" -> WIN
    else -> throw Exception()
}

fun Hand.score(hand: Hand) =
    when (this) {
        ROCK -> when (hand) {
            ROCK -> DRAW
            PAPER -> LOSE
            SCISSORS -> WIN
        }

        PAPER -> when (hand) {
            ROCK -> WIN
            PAPER -> DRAW
            SCISSORS -> LOSE
        }

        SCISSORS -> when (hand) {
            ROCK -> LOSE
            PAPER -> WIN
            SCISSORS -> DRAW
        }
    }

enum class Hand(
    val value: Int,
) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3),
}

enum class Outcome(
    val value: Int,
) {
    WIN(6),
    DRAW(3),
    LOSE(0),
    ;

    fun toDesiredHand(hand: Hand) = when (this) {
        WIN -> when (hand) {
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }

        DRAW -> when (hand) {
            ROCK -> ROCK
            PAPER -> PAPER
            SCISSORS -> SCISSORS
        }

        LOSE -> when (hand) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }
    }
}