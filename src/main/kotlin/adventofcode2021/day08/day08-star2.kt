package adventofcode2021.day08

import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()

    println(inputs.map {
        val (coding, value) = it.split(" | ")
        val valueByCodes = extractDigitCode(coding)

        value.split(" ")
                .map { it.toSet() }
                .map { valueByCodes[it]!! }
                .joinToString("")
                .toInt()
    }.sum())
}

private fun extractDigitCode(code: String): Map<Set<Char>, Int> {
    val codesAsString = code.split(" ")


    val code1 = codesAsString.first { it.length == 2 }.toSet()
    val code4 = codesAsString.first { it.length == 4 }.toSet()
    val code7 = codesAsString.first { it.length == 3 }.toSet()
    val code8 = codesAsString.first { it.length == 7 }.toSet()

    val code9AsString = codesAsString
            .filter { it.length == 6 }
            .filter { it.toSet().containsAll(code4) }
            .first()
    val code9 = code9AsString.toSet()

    val code0AsString = codesAsString
            .filter { it.length == 6 }
            .filter { it != code9AsString }
            .filter { it.toSet().containsAll(code1) }
            .first()
    val code0 = code0AsString.toSet()

    val code6 = codesAsString
            .filter { it.length == 6 }
            .filter { it != code9AsString && it != code0AsString }
            .first().toSet()

    val code5AsString = codesAsString
            .filter { it.length == 5 }
            .filter { code6.containsAll(it.toSet()) }
            .first()
    val code5 = code5AsString.toSet()

    val code3AsString = codesAsString
            .filter { it.length == 5 }
            .filter { it != code5AsString }
            .filter { it.toSet().containsAll(code1) }
            .first()
    val code3 = code3AsString.toSet()

    val code2 = codesAsString
            .filter { it.length == 5 }
            .filter { it != code5AsString && it != code3AsString }
            .first().toSet()

    return mapOf(
            code0 to 0,
            code1 to 1,
            code2 to 2,
            code3 to 3,
            code4 to 4,
            code5 to 5,
            code6 to 6,
            code7 to 7,
            code8 to 8,
            code9 to 9
    )
}