package io.github.hemeroc.kotlinAdventOfCode.util

import java.io.File
import java.math.BigInteger

fun readFile(year: Int, filename: String) =
    File(
        object {}::class.java.getResource("/io/github/hemeroc/kotlinAdventOfCode/year$year/$filename")
            .toURI()
    )

fun readLines(year: Int, filename: String) =
    readFile(year, filename)
        .readLines()

fun readCharacters(year: Int, filename: String) =
    readFile(year, filename)
        .readText()
        .toList()

fun <E> List<E>.partitionIndexed(
    firstPartition: (Int, E) -> E?,
    secondPartition: (Int, E) -> E?,
): Pair<List<E>, List<E>> {
    val first = mutableListOf<E>()
    val second = mutableListOf<E>()
    this.forEachIndexed { index, value ->
        firstPartition(index, value)?.also { first.add(it) }
        secondPartition(index, value)?.also { second.add(it) }
    }
    return Pair(first, second)
}

data class Position(val x: Int, val y: Int) {
    operator fun plus(position: Position) = Position(x + position.x, y + position.y)
    operator fun times(value: Int) = Position(x * value, y * value)
}

fun <T> List<List<T>>.forEachPositioned(function: (Position, T) -> Unit) =
    this.forEachIndexed { x, sub ->
        sub.forEachIndexed { y, element ->
            function(Position(x, y), element)
        }
    }

operator fun <T> List<MutableList<T>>.set(current: Position, value: T) {
    this[current.x][current.y] = value
}

operator fun <T> List<List<T>>.get(current: Position) =
    this[current.x][current.y]

operator fun Int.rem(other: BigInteger): BigInteger = this.toBigInteger() % other


operator fun <K, V> MutableMap<K, V>.set(keys: List<K>, value: V) =
    keys.forEach { key -> this[key] = value }

fun IntRange.cartProd(n: Int): Sequence<List<Int>> {
    val ranges = repeat(n).toList().toTypedArray()
    return cartProd(*(ranges))
}

fun <T : Any> T.repeat(times: Int? = null): Sequence<T> = sequence {
    var count = 0
    while (times == null || count++ < times) yield(this@repeat)
}

fun <T : Any> cartProd(vararg items: Iterable<T>): Sequence<List<T>> = sequence {
    if (items.all { it.iterator().hasNext() }) {
        val itemsIter = items.map { it.iterator() }.filter { it.hasNext() }.toMutableList()
        val currElement: MutableList<T> = itemsIter.map { it.next() }.toMutableList()
        loop@ while (true) {
            yield(currElement.toList())
            for (pos in itemsIter.count() - 1 downTo 0) {
                if (!itemsIter[pos].hasNext()) {
                    if (pos == 0) break@loop
                    itemsIter[pos] = items[pos].iterator()
                    currElement[pos] = itemsIter[pos].next()
                } else {
                    currElement[pos] = itemsIter[pos].next()
                    break
                }
            }
        }
    }
}