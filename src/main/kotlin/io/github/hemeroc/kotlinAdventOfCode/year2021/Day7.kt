package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val data = readLines(2021, "input7.txt")
            .flatMap { line -> line.split(",").map { value -> value.toInt() } }
            .sorted()
        val start = data.minOf { it }
        val end = data.maxOf { it }
        val offset = start
        val fuelConsumptionsSolution1 = IntArray(end - start) { 0 }
        val fuelConsumptionsSolution2 = IntArray(end - start) { 0 }
        fuelConsumptionsSolution1.indices.forEach { index ->
            val movePosition = index + offset
            data.forEach {  currentPosition ->
                fuelConsumptionsSolution1[index] += abs(currentPosition - movePosition)
                fuelConsumptionsSolution2[index] += (abs(currentPosition - movePosition) downTo 1).sum()
            }
        }
        println(fuelConsumptionsSolution1.minOf { it })
        println(fuelConsumptionsSolution2.minOf { it })
    }.also { println("Calculated in ${it}ms") }
}



// HEAP SPACE ERROR
//fun main() {
//    measureTimeMillis {
//        val data = readLines(2021, "input6.txt")
//            .flatMap { line -> line.split(",").map { value -> value.toInt() } }
//            .sorted()
//        val start = data.minOf { it }
//        val end = data.maxOf { it }
//        val offset = start
//        val fuelConsumptions = IntArray(end - start) { 0 }
//        fuelConsumptions.forEach { index ->
//            val movePosition = index + offset
//            data.forEach {  currentPosition ->
//                fuelConsumptions[index] += abs(currentPosition - movePosition)
//            }
//            println(fuelConsumptions)
//        }
//        println(fuelConsumptions.maxOf { it })
//    }.also { println("Calculated in ${it}ms") }
//}