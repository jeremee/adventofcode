package adventofcode2020.day16

data class Criterion(val name: String, val range1: IntRange, val range2: IntRange) {
    fun doesRespectCriteria(value: Int) = value in range1 || value in range2
}