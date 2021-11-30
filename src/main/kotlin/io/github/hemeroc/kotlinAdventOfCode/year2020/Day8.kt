package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import io.github.hemeroc.kotlinAdventOfCode.year2020.Instruction.*
import io.github.hemeroc.kotlinAdventOfCode.year2020.Status.END
import io.github.hemeroc.kotlinAdventOfCode.year2020.Status.LOOP
import java.util.Locale
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        val programInput = readLines(2020, "input8.txt")
        runProgram(programInput).also { (status, accumulator, currentInstruction) ->
            println(
                """
                    Accumulator: $accumulator
                    $status at: $currentInstruction
                """.trimIndent()
            )
        }
        programInput.indices.forEach { instructionToMutate ->
            val (status, accumulator, currentInstruction) = runProgram(programInput) { instructionPointer, instruction ->
                if (instructionToMutate == instructionPointer) {
                    when (instruction) {
                        ACC -> ACC
                        JMP -> NOP
                        NOP -> JMP
                    }
                } else instruction
            }
            if (status == END) {
                println(
                    """
                        Accumulator: $accumulator
                        $status at: $currentInstruction
                    """.trimIndent()
                )
            }
        }
    }.also { println("Calculated in ${it}ms") }
}

private fun runProgram(
    programInput: List<String>,
    instructionMutation: ((Int, Instruction) -> Instruction) = { _, instruction -> instruction }
): Triple<Status, Int, Int> {
    val instructionParser = Regex("(?<instruction>acc|jmp|nop) (?<parameter>[+-]?\\d+)")
    val visitedInstruction = mutableListOf<Int>()
    var accumulator = 0
    var currentInstruction = 0
    while (true) {
        if (currentInstruction >= programInput.size) return Triple(END, accumulator, currentInstruction)
        else if (visitedInstruction.contains(currentInstruction)) return Triple(LOOP, accumulator, currentInstruction)
        visitedInstruction += currentInstruction
        with(instructionParser.matchEntire(programInput[currentInstruction]) ?: throw IllegalArgumentException()) {
            when (instructionMutation(currentInstruction, instruction)) {
                ACC -> {
                    accumulator += parameter
                    currentInstruction++
                }
                JMP -> currentInstruction += parameter
                NOP -> currentInstruction++
            }
        }
    }
}

enum class Instruction { ACC, JMP, NOP }

enum class Status { END, LOOP }

val MatchResult.instruction: Instruction
    get() = Instruction.valueOf(groups["instruction"]?.value?.uppercase(Locale.getDefault()) ?: throw IllegalArgumentException())


val MatchResult.parameter: Int
    get() = groups["parameter"]?.value?.toIntOrNull() ?: throw IllegalArgumentException()
