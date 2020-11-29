package io.github.hemeroc.kotlinAdventOfCode.util

import java.io.File

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
