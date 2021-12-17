package adventofcode2021.day17

data class Area(val xRange: IntRange, val yRange: IntRange) {
    fun isInside(x: Int, y: Int) = x in xRange && y in yRange
}