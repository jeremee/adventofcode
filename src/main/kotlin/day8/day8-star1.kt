import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    val inputs = File("src/main/kotlin/day8/data").readLines()
    val regexp = Regex("(.{3}) ([+-]{1})([0-9]+)")

    val instructions = inputs.map { input ->
        val (instruction, sign, value) = regexp.find(input)?.destructured
            ?: throw IllegalArgumentException("Cannot read $input")
        instruction to (if (sign == "+") value.toInt() else -value.toInt())
    }

    var i = 0
    var acc = 0
    val indexes = mutableListOf<Int>()

    while (!indexes.contains(i)) {
        indexes.add(i)
        val (instruction, value) = instructions[i]
        when(instruction) {
            "nop" -> i++
            "acc" -> {
                acc += value
                i++
            }
            "jmp" -> i += value
            else -> throw IllegalArgumentException("Unknown instruction $instruction")
        }
    }

    println(acc)
}

