package io.github.hemeroc.kotlinAdventOfCode.util

import java.io.File

fun readFile(filename: String) = File(object {}::class.java.getResource(filename).toURI())
fun readLines(filename: String) = readFile(filename).readLines()
