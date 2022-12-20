package adventofcode2022.day09

import java.io.File

fun main(vararg args: String) {
    val moves = File("data").readLines()
        .map {
            it.split(" ").let {
                val move = Direction.valueOf(it.first())
                val number = it.last().toInt()
                (1..number).map { move }
            }
        }.flatten()

    val tailPositions = mutableSetOf<Pos>()
    tailPositions.add(0 to 0)

    var tailPos = 0 to 0
    var headPos = 0 to 0

    moves.forEach {
        headPos = it.move(headPos)
        tailPos = moveTail(headPos, tailPos)
        tailPositions.add(tailPos)

  //      println("head = $headPos - tail = $tailPos")
    }

    println(tailPositions.count())
}

private fun moveTail(headPos: Pos, tailPos: Pos) =
    if (Math.abs(headPos.first - tailPos.first) > 1 || Math.abs(headPos.second - tailPos.second) > 1) {
        val dirX = if (headPos.first > tailPos.first) +1 else -1
        val dirY = if (headPos.second > tailPos.second) +1 else -1
        when {
            Math.abs(headPos.first - tailPos.first) >= 1 && Math.abs(headPos.second - tailPos.second) >= 1 -> Pos(
                tailPos.first + dirX,
                tailPos.second + dirY
            )

            Math.abs(headPos.first - tailPos.first) >= 1 -> Pos(tailPos.first + dirX, tailPos.second)
            else -> Pos(tailPos.first, tailPos.second + dirY)
        }
    } else tailPos


