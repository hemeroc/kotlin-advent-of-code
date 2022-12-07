package io.github.hemeroc.kotlinAdventOfCode.year2022

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.system.measureTimeMillis

private const val CD = "\$ cd "
private const val DIR = "dir "
private const val LS = "$ ls"

fun main() {
    val data = readLines(2022, "input7.txt")
    val paths = mutableMapOf<Path, Long>()
    var path: Path = Paths.get("")
    measureTimeMillis {
        data.forEach {
            when {
                it.startsWith(CD) -> {
                    val dir = it.removePrefix(CD)
                    if (dir == "..") {
                        path = path.parent
                    } else {
                        path += dir
                        paths.putIfAbsent(path, 0L)
                    }
                }

                it.startsWith(DIR) -> paths.putIfAbsent(path + it.removePrefix(DIR), 0L)
                it == LS -> Unit
                else -> {
                    val size = it.split(" ")[0].toLong()
                    updatePathSize(paths, path, size)
                }
            }
        }
        println(paths.values.filter { it < 100_000 }.sum())
        val freeSpace = 70_000_000 -  paths[Paths.get("/")]!!
        val requiredSpace = 30_000_000
        val toFreeSpace = requiredSpace - freeSpace
        println(paths.values.filter { it >= toFreeSpace }.min())
    }.also { println("Calculated in ${it}ms") }
}

private fun updatePathSize(
    paths: MutableMap<Path, Long>,
    path: Path,
    size: Long
) {
    paths[path] = paths[path]!! + size
    path.parent?.let {
        updatePathSize(paths, it, size)
    }
}

operator fun Path?.plus(path: Path): Path =
    this + path.toString()

operator fun Path?.plus(path: String): Path =
    Paths.get(this.toString(), path)!!