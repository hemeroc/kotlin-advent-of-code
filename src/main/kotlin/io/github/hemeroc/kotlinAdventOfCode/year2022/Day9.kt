package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

data class Pos(val x: Int, val y: Int) {
    operator fun plus(modifier: Pos) = Pos(x + modifier.x, y + modifier.y)
}

val UP = Pos(0, -1)
val DOWN = Pos(0, 1)
val LEFT = Pos(-1, 0)
val RIGHT = Pos(1, 0)

fun main() {
    val data = readLines(2022, "input9.txt").flatMap { line ->
        line.split(" ").let { instruction ->
            List(instruction[1].toInt()) {
                when (instruction[0]) {
                    "U" -> UP
                    "D" -> DOWN
                    "L" -> LEFT
                    "R" -> RIGHT
                    else -> throw Exception()
                }
            }
        }
    }
    val rope = MutableList(10) { Pos(0, 0) }
    val visitedTailStartPositions = mutableSetOf(rope[0])
    val visitedTailEndPositions = mutableSetOf(rope[0])
    val maxDistance = sqrt(2.0)
    measureTimeMillis {
        data.forEach { positionModifier ->
            rope[0] += positionModifier
            rope.indices.windowed(2, 1) { (head, tail) ->
                val distance = sqrt((rope[tail].x - rope[head].x).pow(2) + (rope[tail].y - rope[head].y).pow(2))
                if (distance > maxDistance) {
                    rope[tail] = Pos(
                        (rope[head].x - rope[tail].x).sign + rope[tail].x,
                        (rope[head].y - rope[tail].y).sign + rope[tail].y,
                    )
                }
            }
            visitedTailStartPositions.add(rope[1])
            visitedTailEndPositions.add(rope.last())
        }
        println("Solution 1: ${visitedTailStartPositions.size}")
        println("Solution 2: ${visitedTailEndPositions.size}")
    }.also { println("Calculated in ${it}ms") }
}

private fun Int.pow(power: Int) = toDouble().pow(power)
