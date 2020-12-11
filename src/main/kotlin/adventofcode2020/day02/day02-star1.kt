import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()

    val count = inputs.count { input ->
        val (expectedOccurrences, letter, password) = input.split(" ")
        val (minOccurrences, maxOccurrences) = expectedOccurrences.split("-").map(String::toInt)

        val occurrences = password.count { it == letter.first() }
        occurrences in minOccurrences..maxOccurrences
    }
    println(count)
}