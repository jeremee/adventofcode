import java.io.File

fun main() {
    val inputs = File("src/main/kotlin/day6/data").readLines()

    var answersCount = 0
    val answersFound = mutableSetOf<Char>()

    inputs.forEach { line ->
        if (line.isEmpty() || line.isBlank()) {
            answersCount += answersFound.size
            answersFound.clear()
        } else {
            answersFound.addAll(line.asIterable())
        }
    }

    answersCount += answersFound.size
    println(answersCount)
}
