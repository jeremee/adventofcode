import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    val inputs = File("src/main/kotlin/day5/data").readLines()
    println(inputs.map { toSeatValue(it) }.max())
}

private fun toSeatValue(seatId: String) = seatId.fold("") { acc, c ->
    acc + when(c) {
        'B', 'R' -> "1"
        'F', 'L' -> "0"
        else -> IllegalArgumentException("Unknown value $c")
    }
}.let {
    Integer.parseUnsignedInt(it, 2)
}