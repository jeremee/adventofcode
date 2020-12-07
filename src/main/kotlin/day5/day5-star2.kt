import java.io.File

fun main() {
    val inputs = File("src/main/kotlin/day5/data").readLines()
    val sortedSeatIds = inputs
        .map { toSeatValue(it) }
        .sorted()

    val beforeMissing = sortedSeatIds
        .asSequence()
        .filter {
            !sortedSeatIds.contains(it + 1)
        }.first()

    println(beforeMissing + 1)
}

private fun toSeatValue(seatId: String) = seatId.fold("") { acc, c ->
    acc + when (c) {
        'B', 'R' -> "1"
        'F', 'L' -> "0"
        else -> IllegalArgumentException("Unknown value $c")
    }
}.let {
    Integer.parseUnsignedInt(it, 2)
}