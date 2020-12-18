import adventofcode2020.day16.Criterion
import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()

    val criteriaRegexp = Regex("(.*): ([0-9]+-[0-9]+) or ([0-9]+-[0-9]+)")
    val criteria = mutableListOf<Criterion>()
    for (i in inputs.indices) {
        if (inputs[i].isBlank()) break
        val (name, range1, range2) = criteriaRegexp.find(inputs[i])!!.destructured
        criteria.add(Criterion(name, rangeStringToIntRange(range1), rangeStringToIntRange(range2)))
    }

    var scanningErrorRate = 0

    val nearbyTicketsIndex = inputs.indexOf("nearby tickets:") + 1
    for (i in nearbyTicketsIndex until inputs.size) {
        inputs[i].split(',')
            .map(String::toInt)
            .forEach { value ->
                if (criteria.none { it.doesRespectCriteria(value) }) scanningErrorRate += value
            }
    }

    println(scanningErrorRate)
}

private fun rangeStringToIntRange(range: String) =
    range
        .split('-')
        .map(String::toInt)
        .let { IntRange(it[0], it[1]) }