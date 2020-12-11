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
            println(String(map[y].toCharArray()))
        }
        println()
    } while (seatsToSwitch.isNotEmpty())

    val countOccupiedSeat = map.map { line -> line.count { it == OCCUPIED_SEAT } }.sum()
    println("Result = $countOccupiedSeat")
}

private val EMPTY_SEAT = 'L'
private val NO_SEAT = '.'
private val OCCUPIED_SEAT = '#'

private fun canSit(x: Int, y: Int, map: MAP) =
    map[y][x] == EMPTY_SEAT && countVisibleOccupiedSeat(x, y, map) == 0

private fun mustStandUp(x: Int, y: Int, map: MAP) =
    map[y][x] == OCCUPIED_SEAT && countVisibleOccupiedSeat(x, y, map) >= 5

private fun countVisibleOccupiedSeat(x: Int, y: Int, map: MAP) =
    seeSeatInDirection(x, y, map, -1, -1) +
            seeSeatInDirection(x, y, map, -1, 0) +
            seeSeatInDirection(x, y, map, 0, -1) +
            seeSeatInDirection(x, y, map, 1, 0) +
            seeSeatInDirection(x, y, map, 1, 1) +
            seeSeatInDirection(x, y, map, 0, 1) +
            seeSeatInDirection(x, y, map, -1, 1) +
            seeSeatInDirection(x, y, map, 1, -1)


private fun seeSeatInDirection(x: Int, y: Int, map: MAP, moveX: Int, moveY: Int): Int {
    var curX = x + moveX
    var curY = y + moveY
    while (curY >= 0 && curY < map.size &&
        curX >= 0 && curX < map[y].size
    ) {
        if (map[curY][curX] == OCCUPIED_SEAT) return 1
        if (map[curY][curX] == EMPTY_SEAT) return 0

        curX += moveX
        curY += moveY
    }
    return 0
}

private fun applySeatsToSwitch(map: MAP, seatsToSwitch: MutableList<Pos>) {
    seatsToSwitch.map {
        val x = it.x
        val y = it.y
        if (map[y][x] == OCCUPIED_SEAT) map[y][x] = EMPTY_SEAT
        else if (map[y][x] == EMPTY_SEAT) map[y][x] = OCCUPIED_SEAT
    }
}