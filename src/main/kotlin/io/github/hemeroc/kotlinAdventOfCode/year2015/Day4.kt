package io.github.hemeroc.kotlinAdventOfCode.year2015

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val secret = readLines(2015, "input4.txt").first()
    var i = 0
    while (!"$secret$i".md5().startsWith("00000")) i++
    println("""
        Found first AdventCoins at: $i
    """.trimIndent())
    while (!"$secret$i".md5().startsWith("000000")) i++
    println("""
        Found second AdventCoins at: $i
    """.trimIndent())
}

fun String.md5(): String {
    val messageDigest = MessageDigest.getInstance("MD5")
    messageDigest.update(this.toByteArray())
    return String.format("%032x", BigInteger(1, messageDigest.digest()))
}