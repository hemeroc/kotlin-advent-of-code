package io.github.hemeroc.kotlinAdventOfCode.year2015

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import io.github.hemeroc.kotlinAdventOfCode.year2015.State.OFF
import io.github.hemeroc.kotlinAdventOfCode.year2015.State.ON

enum class State {
    ON,
    OFF
}

data class Light(
        private var _state: State = OFF,
        private var _brightness: Int = 0,
) {
    val state
        get() = _state

    val brightness
        get() = _brightness

    fun turnOn() {
        _state = ON
        _brightness++
    }

    fun turnOff() {
        _state = OFF
        if (brightness > 0) _brightness--
    }

    fun toggle() {
        _state = if (_state == ON) OFF else ON
        _brightness += 2
    }
}

fun main() {
    val lightGrid: Array<Array<Light>> = Array(1000) { Array(1000) { Light() } }
    val operation = Regex("(?<command>turn on|turn off|toggle) (?<fromX>\\d+),(?<fromY>\\d+) through (?<toX>\\d+),(?<toY>\\d+)")
    readLines(2015, "input6.txt")
            .forEach {
                val (command, fromX, fromY, toX, toY) = operation.matchEntire(it)!!.destructured
                when (command) {
                    "turn on" -> lightGrid.turnOn(fromX.toInt(), fromY.toInt(), toX.toInt(), toY.toInt())
                    "turn off" -> lightGrid.turnOff(fromX.toInt(), fromY.toInt(), toX.toInt(), toY.toInt())
                    "toggle" -> lightGrid.toggle(fromX.toInt(), fromY.toInt(), toX.toInt(), toY.toInt())
                }
            }
    val lightsOn = lightGrid.map { lightLine ->
        lightLine.sumOf { if (it.state == ON) 1L else 0L }
    }.sum()
    val brightness = lightGrid.map { lightLine ->
        lightLine.sumOf { it.brightness }
    }.sum()
    println("""
        Number of lights turned on: $lightsOn
        Brightness of lights: $brightness
    """.trimIndent())
}

private fun Array<Array<Light>>.turnOn(fromX: Int, fromY: Int, toX: Int, toY: Int) =
        applyOnArea(fromX, fromY, toX, toY) { x, y -> this[x][y].turnOn() }

private fun Array<Array<Light>>.turnOff(fromX: Int, fromY: Int, toX: Int, toY: Int) =
        applyOnArea(fromX, fromY, toX, toY) { x, y -> this[x][y].turnOff() }

private fun Array<Array<Light>>.toggle(fromX: Int, fromY: Int, toX: Int, toY: Int) =
        applyOnArea(fromX, fromY, toX, toY) { x, y -> this[x][y].toggle() }

private fun applyOnArea(fromX: Int, fromY: Int, toX: Int, toY: Int, operation: (Int, Int) -> Unit) {
    (fromX..toX).forEach { x ->
        (fromY..toY).forEach { y ->
            operation.invoke(x, y)
        }
    }
}
