package adventofcode2021.day17

data class Probe(val x: Int, val y: Int, val xVelocity: Int, val yVelocity: Int) {
    fun nextPosition() = Probe(x + xVelocity, y + yVelocity, if (xVelocity > 0) xVelocity - 1 else if (xVelocity < 0) xVelocity + 1 else xVelocity, yVelocity - 1)
    fun reached(area: Area) = area.isInside(x, y)
}