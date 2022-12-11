package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import io.github.hemeroc.kotlinAdventOfCode.util.split
import kotlin.system.measureTimeMillis


class Monkey(
    private val items: MutableList<Long>,
    private val worryOperation: (Long) -> (Long),
    val divisibleByCheck: Long,
    private val passTo: Int,
    private val failTo: Int,
) {
    var checksPerformed = 0L
        private set

    fun checkItemsAndPass(monkeys: List<Monkey>, worryModifier: (Long) -> (Long)) {
        items.forEach {
            checksPerformed++
            val itemWithNewWorry = worryModifier.invoke(worryOperation.invoke(it))
            monkeys[(if (itemWithNewWorry % divisibleByCheck == 0L) passTo else failTo)].items.add(itemWithNewWorry)
        }
        items.clear()
    }
}

fun main() {
    measureTimeMillis {

        val monkeys1 = loadMonkeys()
        repeat(20) {
            monkeys1.forEach { monkey ->
                monkey.checkItemsAndPass(monkeys1) { it / 3 }
            }
        }
        monkeys1.sortedByDescending { it.checksPerformed }.let {
            println(it[0].checksPerformed * it[1].checksPerformed)
        }

        val monkeys2 = loadMonkeys()
        val productOfAllDivisors = monkeys2.map { it.divisibleByCheck }.reduce(Long::times)
        repeat(10_000) {
            monkeys2.forEach { monkey ->
                monkey.checkItemsAndPass(monkeys2) { it % productOfAllDivisors }
            }
        }
        monkeys2.sortedByDescending { it.checksPerformed }.let {
            println(it[0].checksPerformed * it[1].checksPerformed)
        }
    }.also { println("Calculated in ${it}ms") }
}

private fun loadMonkeys() = readLines(2022, "input11.txt").split { it.isEmpty() }.map { monkey ->
    Monkey(
        monkey[1].removePrefix("  Starting items: ").split(", ").map { it.toLong() }.toMutableList(),
        monkey[2].removePrefix("  Operation: new = old ").split(" ").let {
            val modifier = if (it[1] == "old") null else it[1].toLong()
            when (it[0]) {
                "+" -> {
                    { i -> i + (modifier ?: i) }
                }

                "*" -> {
                    { i -> i * (modifier ?: i) }
                }

                else -> throw Exception()
            }
        },
        monkey[3].substringAfterLast(" ").toLong(),
        monkey[4].substringAfterLast(" ").toInt(),
        monkey[5].substringAfterLast(" ").toInt(),
    )
}
