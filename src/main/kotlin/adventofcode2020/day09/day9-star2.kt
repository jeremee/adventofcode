import java.io.File
import java.lang.IllegalStateException

fun main(vararg args: String) {
    val inputs = File("data").readLines()
    val preambleSize = 25

    val values = inputs.map(String::toLong)

    val weaknessNumber = findWeaknessNumber(values, preambleSize)

    val contiguousNumbersThatSumWeaknessNumber = findContiguousNumbersThatSum(weaknessNumber, values)

    println(contiguousNumbersThatSumWeaknessNumber.min()!! + contiguousNumbersThatSumWeaknessNumber.max()!!)

}

private fun findWeaknessNumber(values: List<Long>, preambleSize: Int): Long {
    for (i in preambleSize until values.size) {
        val value = values[i]
        if (!hasPairThatSumValue(value, values.subList(i - preambleSize, i))) return value
    }
    throw IllegalStateException("No weakness number found")
}

private fun hasPairThatSumValue(value: Long, values: List<Long>): Boolean {
    for (i in values.indices) {
        for (j in i + 1 until values.size) {
            if (values[i] + values[j] == value) return true
        }
    }
    return false
}

private fun findContiguousNumbersThatSum(weaknessNumber: Long, values: List<Long>): List<Long> {
    for(i in 0 until values.size-1) {
        for (j in i+1 until values.size) {
            val contiguousValues = values.subList(i, j)
            if (contiguousValues.sum() == weaknessNumber) return contiguousValues
        }
    }
    throw IllegalStateException("No contiguous values found can be summed to obtain weakness number")
}