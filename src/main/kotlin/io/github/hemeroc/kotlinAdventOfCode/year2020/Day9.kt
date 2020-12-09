package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val lastN = 25
        val programInput = readLines(2020, "input9.txt").map { it.toLong() }
        (lastN until programInput.size).forEach { index ->
            val check = programInput[index]
            val preList = programInput.subList(index - lastN, index)
            val tupleSum = preList.tupleSum()
            if (!tupleSum.contains(check)) {
                println(
                    """
                        Invalid number: $check
                        Encryption weakness: ${breakEncryption(programInput, check)?.let { it.first + it.second }}
                    """.trimIndent()
                )
                return@measureTimeMillis
            }
        }
    }.also { println("Calculated in ${it}ms") }
}

fun breakEncryption(list: List<Long>, check: Long): Pair<Long, Long>? {
    (0 until list.size - 2).forEach { fromIndex ->
        (fromIndex + 2 until list.size).forEach { toIndex ->
            with(list.subList(fromIndex, toIndex)) {
                if (sum() == check)
                    return Pair(
                        minOrNull() ?: throw RuntimeException(),
                        maxOrNull() ?: throw RuntimeException(),
                    )
            }
        }
    }
    return null
}

private fun List<Long>.tupleSum(): Set<Long> {
    val tupleSums = mutableSetOf<Long>()
    val distinct = this.toSet().distinct()
    distinct.forEachIndexed { index, element ->
        distinct.subList(index + 1, size).forEach { otherElement ->
            tupleSums.add(element + otherElement)
        }
    }
    return tupleSums
}
