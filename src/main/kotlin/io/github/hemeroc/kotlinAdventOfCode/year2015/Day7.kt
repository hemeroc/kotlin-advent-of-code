package io.github.hemeroc.kotlinAdventOfCode.year2015

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import java.util.*
import kotlin.collections.HashMap

val instruction = Regex("((?<leftInput>(?<leftOperand>\\S+)?.*) )?(?<operator>AND|OR|LSHIFT|RSHIFT|NOT|->)( (?<rightInput>(?<rightOperand>\\S+)?.*))?")

@ExperimentalUnsignedTypes
val signalMap = HashMap<String, UShort>()


@ExperimentalUnsignedTypes
fun main() {
    val instuctions1 = LinkedList(readLines(2015, "input7.1.txt").toMutableList())
    while (instuctions1.size > 0) {
        val input = instuctions1.remove()
        if (parse(input) == null) instuctions1.add(input)
    }
    println(signalMap["a"])
    signalMap.clear()
    val instuctions2 = LinkedList(readLines(2015, "input7.2.txt").toMutableList())
    while (instuctions2.size > 0) {
        val input = instuctions2.remove()
        if (parse(input) == null) instuctions2.add(input)
    }
    println(signalMap["a"])
}

@ExperimentalUnsignedTypes
fun parse(input: String): String? {
    val matchEntire = instruction.matchEntire(input) ?: throw IllegalStateException()
    val operator = matchEntire.groups["operator"]?.value ?: ""
    val leftInput = matchEntire.groups["leftInput"]?.value ?: ""
    val leftOperand = matchEntire.groups["leftOperand"]?.value ?: ""
    val rightInput = matchEntire.groups["rightInput"]?.value ?: ""
    val rightOperand = matchEntire.groups["rightOperand"]?.value ?: ""
    return when (operator) {
        "->" -> {
            val left = value(if (leftInput == leftOperand) leftInput else parse(leftInput))
            val right = if (rightInput == rightOperand) rightInput else parse(rightInput)
            if (right != null && left != null) {
                signalMap[right] = left.toUShort()
                left.toString()
            } else null
        }
        "AND" -> {
            val leftValue = value(if (leftInput == leftOperand) leftInput else parse(leftInput))
            val rightValue = value(if (rightInput == rightOperand) rightInput else parse(rightInput))
            if (leftValue != null && rightValue != null) {
                (leftValue and rightValue).toString()
            } else null
        }
        "OR" -> {
            val leftValue = value(if (leftInput == leftOperand) leftInput else parse(leftInput))
            val rightValue = value(if (rightInput == rightOperand) rightInput else parse(rightInput))
            if (leftValue != null && rightValue != null) {
                (leftValue or rightValue).toString()
            } else null
        }
        "LSHIFT" -> {
            val leftValue = value(if (leftInput == leftOperand) leftInput else parse(leftInput))
            val rightValue = value(if (rightInput == rightOperand) rightInput else parse(rightInput))
            if (leftValue != null && rightValue != null) {
                (leftValue.toInt() shl rightValue.toInt()).toString()
            } else null
        }
        "RSHIFT" -> {
            val leftValue = value(if (leftInput == leftOperand) leftInput else parse(leftInput))
            val rightValue = value(if (rightInput == rightOperand) rightInput else parse(rightInput))
            if (leftValue != null && rightValue != null) {
                (leftValue.toInt() shr rightValue.toInt()).toString()
            } else null
        }
        "NOT" -> {
            val rightValue = value(if (rightInput == rightOperand) rightInput else parse(rightInput))
            if (rightValue != null) {
                (rightValue.inv()).toString()
            } else null
        }
        else -> throw IllegalStateException()
    }
}

@ExperimentalUnsignedTypes
fun value(token: String?) = token?.toUShortOrNull() ?: signalMap[token]
