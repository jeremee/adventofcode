import java.io.File

fun main() {
    val inputs = File("src/main/kotlin/day6/data").readLines()

    var answersCount = 0
    val answersFound = mutableSetOf<Char>()
    var firstOfGroup = true

    inputs.forEach { line ->
        if (line.isEmpty() || line.isBlank()) {
            answersCount += answersFound.size
            firstOfGroup = true
        } else {
            if (firstOfGroup) {
                firstOfGroup = false
                answersFound.clear()
                answersFound.addAll(line.asIterable())
            } else {
                answersFound.intersect(line.asIterable()).let {
                    answersFound.clear()
                    answersFound.addAll(it)
                }
            }
        }
    }

    answersCount += answersFound.size
    println(answersCount)
}
