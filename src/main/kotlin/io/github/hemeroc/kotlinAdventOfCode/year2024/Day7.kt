package io.github.hemeroc.kotlinAdventOfCode.year2024

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.math.log10
import kotlin.math.pow
import kotlin.time.measureTime

fun main() {
    val data = readLines(2024, "input7.txt")
        .map { line ->
            line
                .split(':', ' ')
                .filter { it.isNotEmpty() }
                .map { it.toLong() }
        }
    val operations = listOf<(Long, Long) -> Long>(Long::plus, Long::times)
    val concatenate: (Long, Long) -> Long = { l, r -> (l * 10.0.pow(log10(r.toDouble()).toInt() + 1) + r).toLong() }
    measureTime {
        data
            .filter {
                val numbers = it.tail()
                solve(it.head(), numbers.head(), numbers.tail(), operations)
            }
            .sumOf { it.head() }
            .also { print("Part 1: $it ") }
    }.also { println("Calculated in ${it}") }
    measureTime {
        data
            .filter {
                val numbers = it.tail()
                solve(it.head(), numbers.head(), numbers.tail(), operations + concatenate)
            }
            .sumOf { it.head() }
            .also { print("Part 2: $it ") }
    }.also { println("Calculated in ${it}") }
    measureTime {
        data
            .filter {
                val numbers = it.tail()
                solve1(it.head(), numbers.head(), numbers.tail())
            }
            .sumOf { it.head() }
            .also { print("Part 1: $it ") }
    }.also { println("Calculated in ${it}") }
    measureTime {
        data
            .filter {
                val numbers = it.tail()
                solve2(it.head(), numbers.head(), numbers.tail())
            }
            .sumOf { it.head() }
            .also { print("Part 2: $it ") }
    }.also { println("Calculated in ${it}") }
}

fun <T> List<T>.head() = first()
fun <T> List<T>.tail() = drop(1)

private fun solve(
    target: Long,
    accumulator: Long,
    numbers: List<Long>,
    operations: List<(Long, Long) -> Long>
): Boolean =
    when {
        target < accumulator -> false
        numbers.isEmpty() -> target == accumulator
        else -> operations.any { solve(target, it(accumulator, numbers.head()), numbers.tail(), operations) }
    }

private fun solve1(
    target: Long,
    accumulator: Long,
    numbers: List<Long>,
): Boolean {
    if (target < accumulator) return false
    if (numbers.isEmpty()) return target == accumulator
    val head = numbers.head()
    val tail = numbers.tail()
    return solve1(target, accumulator + head, tail) || solve1(target, accumulator * head, tail)
}

private fun solve2(
    target: Long,
    accumulator: Long,
    numbers: List<Long>,
): Boolean {
    if (target < accumulator) return false
    if (numbers.isEmpty()) return target == accumulator
    val head = numbers.head()
    val tail = numbers.tail()
    return solve2(target, accumulator + head, tail) ||
            solve2(target, accumulator * head, tail) ||
            solve2(target, (accumulator * 10.0.pow(log10(head.toDouble()).toInt() + 1) + head).toLong(), tail)
}
