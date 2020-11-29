package io.github.hemeroc.kotlinAdventOfCode.year2019

import io.github.hemeroc.kotlinAdventOfCode.util.readFile
import java.lang.RuntimeException

fun main() {
    println(executeProgram("input2.txt", 12, 2))
    for (noun in 1..100) {
        for (verb in 1..100) {
            if (executeProgram("input2.txt", noun, verb) == 19690720) {
                println("noun = $noun, verb = $verb, 100 * noun + verb = ${100 * noun + verb}")
                break
            }
        }
    }
}

private fun executeProgram(filename: String, noun: Int? = null, verb: Int? = null, delimiter: String = ","): Int {
    val program = readFile(2019, filename).readText().split(delimiter)
            .map { it.toInt() }
            .toIntArray()
            .also {
                if (noun != null) {
                    it[1] = noun
                }
                if (verb != null) {
                    it[2] = verb
                }
            }

    var instructionPointer = 0;
    while (instructionPointer >= 0) {
        val opcode = program[instructionPointer]
        instructionPointer = when (opcode) {
            1 -> operate(program, instructionPointer) { left, right -> left + right }
            2 -> operate(program, instructionPointer) { left, right -> left * right }
            99 -> -1
            else -> throw RuntimeException()
        }
    }
    return program[0]
}

private fun operate(program: IntArray, instructionPointer: Int, operation: (Int, Int) -> Int): Int {
    val leftPos = program[instructionPointer + 1]
    val rightPos = program[instructionPointer + 2]
    val finalPos = program[instructionPointer + 3]
    val leftVal = program[leftPos]
    val rightVal = program[rightPos]
    program[finalPos] = operation.invoke(leftVal, rightVal)
    return instructionPointer + 4
}
