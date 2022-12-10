package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    val data = readLines(2022, "input10.txt")
    measureTimeMillis {
        var x = 1
        val screen = MutableList<Char>(400) { ' ' }
        val sum = data.flatMap {
            when (it) {
                "noop" -> listOf(0)
                else -> listOf(0, it.split(" ")[1].toInt())
            }
        }.mapIndexed { index, operation ->
            val cycle = index + 1
            when (cycle) {
                20, 60, 100, 140, 180, 220 -> cycle * x
                else -> 0
            }.also {
                screen[cycle-1] = if ((cycle-1)%40 in (x - 1..x + 1)) {
                    '#'
                } else {
                    '.'
                }
                x += operation
            }
        }.sum()
        println(sum)
        screen.forEachIndexed { index, it ->
            print(it)
            if((index+1)%40==0) {
                println()
            }
        }
    }.also { println("Calculated in ${it}ms") }
}
