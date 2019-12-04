package io.github.hemeroc.kotlinAdventOfCode.util

import java.io.File

fun file(filename: String) = File(object {}::class.java.getResource(filename).toURI())
fun lines(filename: String) = file(filename).readLines()
