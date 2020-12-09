import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()

    val count = inputs.count { input ->
        val (occurrences, letter, password) = input.split(" ")
        val (min, max) = occurrences.split("-").map(String::toInt)

        val occurences = password.count { it == letter.first() }
        occurences in min..max
    }
    println(count)
}