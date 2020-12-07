import java.io.File

fun main() {
    val inputs = File("data/day2").readLines()

    val count = inputs.count { input ->
        val (occurrences, letter, password) = input.split(" ")
        val (min, max) = occurrences.split("-").map(String::toInt)

        val occurences = password.count { it == letter.first() }
        occurences in min..max
    }
    println(count)
}