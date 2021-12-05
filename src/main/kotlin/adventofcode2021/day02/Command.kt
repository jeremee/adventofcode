package adventofcode2021.day02

data class Command(val value: Int, val direction: Direction) {
    enum class Direction { up, down, forward }

    constructor(direction: String, value: Int) : this(value, Direction.valueOf(direction))
}