import adventofcode2020.day11.MAP
import adventofcode2020.day11.Pos
import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()
    val map = inputs.map(String::toCharArray).map(CharArray::toMutableList)

    val seatsToSwitch = mutableListOf<Pos>()

    do {
        applySeatsToSwitch(map, seatsToSwitch)
        seatsToSwitch.clear()
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (canSit(x, y, map) || mustStandUp(x, y, map))
                    seatsToSwitch.add(Pos(x, y))
            }
        }
    } while (seatsToSwitch.isNotEmpty())

    val countOccupiedSeat = map.map { line -> line.count { it == OCCUPIED_SEAT } }.sum()
    println(countOccupiedSeat)
}

private val EMPTY_SEAT = 'L'
private val NO_SEAT = '.'
private val OCCUPIED_SEAT = '#'

private fun canSit(x: Int, y: Int, map: MAP) =
    map[y][x] == EMPTY_SEAT && countAdjacentOccupiedSeat(x, y, map) == 0

private fun mustStandUp(x: Int, y: Int, map: MAP) =
    map[y][x] == OCCUPIED_SEAT && countAdjacentOccupiedSeat(x, y, map) >= 4

private fun countAdjacentOccupiedSeat(x: Int, y: Int, map: MAP): Int {
    var occupiedSeat = 0
    if (y > 0) {
        occupiedSeat += countAdjacentOccupiedSeatOnLine(x, map[y - 1])
    }
    occupiedSeat += countAdjacentOccupiedSeatOnLine(x, map[y], excludeCenter = true)

    if (y < map.size - 1) {
        occupiedSeat += countAdjacentOccupiedSeatOnLine(x, map[y + 1])
    }
    return occupiedSeat
}

private fun countAdjacentOccupiedSeatOnLine(x: Int, line: List<Char>, excludeCenter: Boolean = false) =
    (if (x > 0 && line[x - 1] == OCCUPIED_SEAT) 1 else 0) +
            (if (!excludeCenter && line[x] == OCCUPIED_SEAT) 1 else 0) +
            (if (x < line.size - 1 && line[x + 1] == OCCUPIED_SEAT) 1 else 0)

private fun applySeatsToSwitch(map: MAP, seatsToSwitch: MutableList<Pos>) {
    seatsToSwitch.map {
        val x = it.x
        val y = it.y
        if (map[y][x] == OCCUPIED_SEAT) map[y][x] = EMPTY_SEAT
        else if (map[y][x] == EMPTY_SEAT) map[y][x] = OCCUPIED_SEAT
    }
}