package io.github.hemeroc.kotlinAdventOfCode.year2021

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import java.lang.Integer.parseInt
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val diagnosticReport = readLines(2021, "input3.txt")
        val size = diagnosticReport[0].length
        val zeros = Array(size) { 0 }
        val ones = Array(size) { 0 }
        val result = Array(size) { '0' }
        val resultInv = Array(size) { '0' }
        diagnosticReport.calculateZerosAndOnes(zeros, ones, result, resultInv)
        println(parseInt(result.joinToString(separator = ""), 2) * parseInt(resultInv.joinToString(separator = ""), 2))
        val oxygen = diagnosticReport.toMutableList()
        val co2Scrubber = diagnosticReport.toMutableList()
        (0 until size).forEach { index ->
            oxygen.removeIf { oxygen.size > 1 && it[index] != result[index] }
            oxygen.calculateZerosAndOnes(zeros, ones, result, resultInv)
        }
        (0 until size).forEach { index ->
            co2Scrubber.removeIf { co2Scrubber.size > 1 && it[index] != resultInv[index] }
            co2Scrubber.calculateZerosAndOnes(zeros, ones, result, resultInv)
        }
        println(parseInt(oxygen.single(), 2) * parseInt(co2Scrubber.single(), 2))
    }.also { println("Calculated in ${it}ms") }
}

private fun List<String>.calculateZerosAndOnes(
    zeros: Array<Int>,
    ones: Array<Int>,
    result: Array<Char>,
    resultInv: Array<Char>
) {
    zeros.fill(0)
    ones.fill(0)
    forEach { input ->
        input.indices.forEach {
            if (input[it] == '0') zeros[it]++ else ones[it]++
        }
    }
    zeros.indices.forEach {
        if (zeros[it] > ones[it]) {
            result[it] = '0'
            resultInv[it] = '1'
        } else {
            result[it] = '1'
            resultInv[it] = '0'
        }
    }
}
