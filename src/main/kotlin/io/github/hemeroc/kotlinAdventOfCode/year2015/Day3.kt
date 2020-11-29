package io.github.hemeroc.kotlinAdventOfCode.year2015

import io.github.hemeroc.kotlinAdventOfCode.util.readCharacters

data class MutableLocation(
        var x: Int,
        var y: Int,
)

data class Location(
        val x: Int,
        val y: Int,
)

fun MutableLocation.toLocation() = Location(this.x, this.y)

fun main() {
    val instructions = readCharacters(2015, "input3.txt")
    val singleSanta = MutableLocation(0, 0)
    val santaLocation = MutableLocation(0, 0)
    val roboSantaLocation = MutableLocation(0, 0)
    val numberOfUniqueHousesSingleSants = navigateHouses(instructions, singleSanta)
    val numberOfUniqueHousesMultipleSantas = navigateHouses(instructions, santaLocation, roboSantaLocation)
    println("""
        Number of unique houses when santa flies alone: $numberOfUniqueHousesSingleSants
        Number of unique houses when multiple santa's fly: $numberOfUniqueHousesMultipleSantas
    """.trimIndent())
}

private fun navigateHouses(instructions: List<Char>, vararg flyers: MutableLocation): Int {
    val uniqueHouses = mutableSetOf<Location>()
    flyers.forEach { uniqueHouses.add(it.toLocation()) }
    instructions.forEachIndexed { index, instruction ->
        val currentLocation = flyers[index % flyers.size]
        when (instruction) {
            '>' -> currentLocation.x++
            '<' -> currentLocation.x--
            '^' -> currentLocation.y++
            'v' -> currentLocation.y--
        }
        uniqueHouses.add(currentLocation.toLocation())
    }
    return uniqueHouses.size
}
