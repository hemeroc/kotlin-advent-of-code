package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    val data = readLines(2022, "input3.txt")
    measureTimeMillis {
        data.sumOf { backpack ->
            backpack.toList()
                .chunked(backpack.length / 2)
                .let { (first, second) ->
                    first.intersect(second.toSet()).single().priority
                }
        }.also {
            println(it)
        }
    }.also { println("Calculated in ${it}ms") }
    measureTimeMillis {
        data
            .map { it.toSet() }
            .chunked(3)
            .sumOf {backpacks ->
                backpacks.reduce { left, right -> left.intersect(right) }.single().priority
            }
            .also {
                println(it)
            }
    }.also { println("Calculated in ${it}ms") }
}

private val Char.priority: Int
    get() =
        if (isLowerCase()) {
            code - 'a'.code + 1
        } else {
            code - 'A'.code + 27
        }
