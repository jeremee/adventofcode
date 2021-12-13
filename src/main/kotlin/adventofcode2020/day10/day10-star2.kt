import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()
    val values = inputs.map(String::toInt)
        .sorted()
        .toMutableList()
    values.add(values.last() + 3)

    var contiguousGroupSize = 1
    var combinationCount = 1L
    for (i in 1..values.last()) {
        if (!values.contains(i)) { // If we are at the end of a contiguous group of adapters
            if (contiguousGroupSize != 0)
                combinationCount *= computeCombination(contiguousGroupSize)
            contiguousGroupSize = 0
        } else {
            contiguousGroupSize++
        }
    }

    println(combinationCount)
}

private val size1 = 1L
private val size2 = 1L
private val size3 = 2L
private val combinations = mutableListOf(size1, size2, size3)

private fun computeCombination(groupSize: Int): Long {
    if (combinations.size >= groupSize) return combinations[groupSize - 1]

    for (i in combinations.size until groupSize) {
        combinations.add(combinations[i - 1] + combinations[i - 2] + combinations[i - 3])
    }
    return combinations.last()
}
