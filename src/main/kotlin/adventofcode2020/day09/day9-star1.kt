import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()
    val preambleSize = 25

    val values = inputs.map(String::toLong)

    for (i in preambleSize until values.size) {
        val value = values[i]
        if (!hasPairThatSumValue(value, values.subList(i - preambleSize, i))) {
            println(value)
            break
        }
    }
}

private fun hasPairThatSumValue(value: Long, values: List<Long>): Boolean {
    for (i in values.indices) {
        for (j in i + 1 until values.size) {
            if (values[i] + values[j] == value) return true
        }
    }
    return false
}