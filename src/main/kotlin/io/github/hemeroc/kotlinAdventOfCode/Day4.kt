package io.github.hemeroc.kotlinAdventOfCode

fun main() {
    println(
            (158126..624574)
                    .filter { it.hasTwoEqualAdjacentDigits() }
                    .filter { it.digitsDoNotDecrease() }
                    .count()
    )
    println(
            (158126..624574)
                    .filter { it.hasExactlyTwoEqualAdjacentDigits() }
                    .filter { it.digitsDoNotDecrease() }
                    .count()
    )
}

private fun Int.digitsDoNotDecrease(): Boolean {
    var number = this
    var lastDigit = 9
    while (number > 0) {
        val nextDigit = number % 10
        if (nextDigit > lastDigit) return false
        lastDigit = nextDigit
        number /= 10
    }
    return true
}

private fun Int.hasTwoEqualAdjacentDigits(): Boolean {
    var number = this
    var lastDigit : Int? = null
    while (number > 0) {
        val nextDigit = number % 10
        if (nextDigit == lastDigit) return true
        lastDigit = nextDigit
        number /= 10
    }
    return false
}

private fun Int.hasExactlyTwoEqualAdjacentDigits(): Boolean {
    var number = this
    var lastDigit : Int? = null
    var numberOfEqualAdjacentDigits = 0
    while (number > 0) {
        val nextDigit = number % 10
        if (nextDigit == lastDigit) {
            numberOfEqualAdjacentDigits++
        } else {
            if(numberOfEqualAdjacentDigits == 2) {
                return true
            }
            numberOfEqualAdjacentDigits = 1
        }
        lastDigit = nextDigit
        number /= 10
    }
    return numberOfEqualAdjacentDigits == 2
}
