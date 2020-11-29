package io.github.hemeroc.kotlinAdventOfCode.year2015

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

fun main() {
    val simpleNiceStringRegex = simpleNiceStringRegex()
    val advancedNiceStringRegex = advancedNiceStringRegex()
    var simpleNiceStrings = 0
    var advancedNiceStrings = 0
    val readLines = readLines(2015, "input5.txt")
    val measureTimeMillis = measureTimeMillis {
        readLines
                .forEach {
                    if (simpleNiceStringRegex.matches(it)) simpleNiceStrings++
                    if (advancedNiceStringRegex.matches(it)) advancedNiceStrings++
                }
    }
    println("""
        Number of simple nice strings: $simpleNiceStrings
        Number of advanced nice strings: $advancedNiceStrings
        Milliseconds needed: $measureTimeMillis
    """.trimIndent())
}

private fun simpleNiceStringRegex(): Regex {
    val doubleLetters = "(?=\\S*(?<double>\\S)\\k<double>\\S*)"
    val naughtyLetters = "(?!\\S*(ab|cd|pq|xy)\\S*)"
    val niceLetters = "(?=(\\S*[aeiou]\\S*){3,})"
    return Regex("$doubleLetters$naughtyLetters$niceLetters\\S*")
}

fun advancedNiceStringRegex(): Regex {
    val twoLettersAppearingTwice = "(?=\\S*(?<double>\\S\\S)\\S*\\k<double>\\S*)"
    val oneLetterBetweenDoubleLetter = "(?=\\S*(?<around>\\S)\\S\\k<around>\\S*)"
    return Regex("$twoLettersAppearingTwice$oneLetterBetweenDoubleLetter\\S*")
}