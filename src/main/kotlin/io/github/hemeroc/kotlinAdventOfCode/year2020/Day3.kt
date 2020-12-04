package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines

data class Slope(
    val right: Int,
    val down: Int,
)

fun main() {
    val trees = readLines(2020, "input3.txt")
    val sumTreesOneSlope = trees
        .mapIndexed { index, treeLine -> countTrees(index, treeLine, Slope(3, 1)) }
        .sum()
    val sumTreesMultiSlope = listOf(
        Slope(1, 1),
        Slope(3, 1),
        Slope(5, 1),
        Slope(7, 1),
        Slope(1, 2),
    ).map { slope ->
        trees
            .mapIndexed { index, treeLine -> countTrees(index, treeLine, slope) }
            .sum()
            .toLong()
    }.product()
    println(
        """
        Encountered trees one slope: $sumTreesOneSlope
        Encountered trees multi slope: $sumTreesMultiSlope
    """.trimIndent()
    )
}

fun countTrees(index: Int, trees: String, slope: Slope) =
    if (index > 0 &&
        index % slope.down == 0 &&
        trees[(index / slope.down) * slope.right % trees.length] == '#'
    ) 1 else 0

private fun List<Long>.product() = this.reduce { left, right -> left * right }


