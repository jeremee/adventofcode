import java.io.File

fun main() {
    val inputs = File("src/main/kotlin/day8/data").readLines()
    val regexp = Regex("(.{3}) ([+-]{1})([0-9]+)")

    val instructions = inputs.map { input ->
        val (instruction, sign, value) = regexp.find(input)?.destructured
            ?: throw IllegalArgumentException("Cannot read $input")
        instruction to (if (sign == "+") value.toInt() else -value.toInt())
    }

    for (i in instructions.indices) {
        val (instruction, value) = instructions[i]
        if (instruction == "nop" || instruction == "jmp") {
            val mutatedInstructions = mutableListOf<Pair<String, Int>>()
            mutatedInstructions.addAll(instructions)
            when (instruction) {
                "nop" -> mutatedInstructions[i] = "jmp" to value
                "jmp" -> mutatedInstructions[i] = "nop" to value
                else -> throw IllegalStateException("Cannot mutate instruction $instruction")
            }

            getAccIndex(mutatedInstructions)?.let {
                println(it)
                System.exit(0)
            }
        }
    }
}

private fun getAccIndex(instructions: List<Pair<String, Int>>): Int? {
    var i = 0
    var acc = 0
    val indexes = mutableListOf<Int>()

    while (!indexes.contains(i)) {
        indexes.add(i)
        val (instruction, value) = instructions[i]
        when (instruction) {
            "nop" -> i++
            "acc" -> {
                acc += value
                i++
            }
            "jmp" -> i += value
            else -> throw IllegalArgumentException("Unknown instruction $instruction")
        }
        if (i == instructions.size) return acc
    }

    return null
}

