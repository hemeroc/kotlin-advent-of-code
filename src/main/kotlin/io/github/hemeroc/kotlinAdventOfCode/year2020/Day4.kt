package io.github.hemeroc.kotlinAdventOfCode.year2020

import io.github.hemeroc.kotlinAdventOfCode.util.readLines
import kotlin.system.measureTimeMillis

class PassportValidator {
    var byr: String? = null
    var iyr: String? = null
    var eyr: String? = null
    var hgt: String? = null
    var hcl: String? = null
    var ecl: String? = null
    var pid: String? = null
    var cid: String? = null
    private val hgtRegex = Regex("((?<numberCm>\\d+)cm)|((?<numberInch>\\d+)in)")
    private val hclRegex = Regex("#[a-f0-9]{6}")
    private val eclRegex = Regex("amb|blu|brn|gry|grn|hzl|oth")
    private val pidRegex = Regex("\\d{9}")

    fun reset() {
        byr = null
        iyr = null
        eyr = null
        hgt = null
        hcl = null
        ecl = null
        pid = null
        cid = null
    }

    fun validateSoft() =
        byr != null &&
                iyr != null &&
                eyr != null &&
                hgt != null &&
                hcl != null &&
                ecl != null &&
                pid != null

    fun validateStrong() =
        validBYR() && validIYR() && validEYR() && validHGT() && validHCL() && validECL() && validPID() && validCID()

    private fun validBYR() =
        when (byr?.toIntOrNull()) {
            in 1920..2002 -> true
            else -> false
        }

    private fun validIYR() =
        when (iyr?.toIntOrNull()) {
            in 2010..2020 -> true
            else -> false
        }

    private fun validEYR() =
        when (eyr?.toIntOrNull()) {
            in 2020..2030 -> true
            else -> false
        }

    private fun validHGT(): Boolean {
        val matchEntire = hgtRegex.matchEntire(hgt ?: "")
        val cm = matchEntire?.groups?.get("numberCm")?.value?.toIntOrNull()
        val inch = matchEntire?.groups?.get("numberInch")?.value?.toIntOrNull()
        if (cm != null && cm in 150..193) return true
        if (inch != null && inch in 59..76) return true
        return false
    }

    private fun validHCL() = hcl?.matches(hclRegex) ?: false

    private fun validECL() = ecl?.matches(eclRegex) ?: false

    private fun validPID() = pid?.matches(pidRegex) ?: false

    private fun validCID() = true
}

fun main() {
    measureTimeMillis {
        var softValidPassports = 0
        var strongValidPassports = 0
        val passportValidator = PassportValidator()
        val passwordParser = Regex(
            "(" +
                    "(byr:(?<byr>\\S+)[\\s]*)|" +
                    "(iyr:(?<iyr>\\S+)[\\s]*)|" +
                    "(eyr:(?<eyr>\\S+)[\\s]*)|" +
                    "(hgt:(?<hgt>\\S+)[\\s]*)|" +
                    "(hcl:(?<hcl>\\S+)[\\s]*)|" +
                    "(ecl:(?<ecl>\\S+)[\\s]*)|" +
                    "(pid:(?<pid>\\S+)[\\s]*)|" +
                    "(cid:(?<cid>\\S+)[\\s]*)" +
                    ")*.*"
        )
        (readLines(2020, "input4.txt") + "").forEach { dataLine ->
            if (dataLine.isBlank()) {
                softValidPassports += if (passportValidator.validateSoft()) 1 else 0
                strongValidPassports += if (passportValidator.validateStrong()) 1 else 0
                passportValidator.reset()
            } else {
                with(passwordParser.matchEntire(dataLine) ?: return@forEach) {
                    passportValidator.byr = this.groups["byr"]?.value ?: passportValidator.byr
                    passportValidator.iyr = this.groups["iyr"]?.value ?: passportValidator.iyr
                    passportValidator.eyr = this.groups["eyr"]?.value ?: passportValidator.eyr
                    passportValidator.hgt = this.groups["hgt"]?.value ?: passportValidator.hgt
                    passportValidator.hcl = this.groups["hcl"]?.value ?: passportValidator.hcl
                    passportValidator.ecl = this.groups["ecl"]?.value ?: passportValidator.ecl
                    passportValidator.pid = this.groups["pid"]?.value ?: passportValidator.pid
                    passportValidator.cid = this.groups["cid"]?.value ?: passportValidator.cid
                }
            }

        }
        println(
            """
                Soft valid passwords: $softValidPassports
                Strong valid passwords: $strongValidPassports
            """.trimIndent()
        )
    }.also { println("Calculated in ${it}ms") }
}
