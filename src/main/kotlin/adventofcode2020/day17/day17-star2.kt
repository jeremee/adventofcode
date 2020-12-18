import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()
    val initialPos = inputs.mapIndexed { y, line ->
        line.mapIndexed { x, char -> if (char == '#') Pos4D(x, y, 0, 0) else null }
            .filterNotNull()
    }.flatten()

    val actives = mutableSetOf<Pos4D>()
    actives.addAll(initialPos)

    val toActivate = mutableSetOf<Pos4D>()
    val toDeactivate = mutableSetOf<Pos4D>()

    for (i in 1..6) {
        println("PASS $i: count actives = ${actives.count()}")

        actives.forEach { activePos ->
            if (!keepActive(activePos, actives)) toDeactivate.add(activePos)
            activePos.neighbors().forEach { neighborPos ->
                if (shouldActivate(neighborPos, actives)) toActivate.add(neighborPos)
            }
        }

        actives.addAll(toActivate)
        actives.removeAll(toDeactivate)

        toActivate.clear()
        toDeactivate.clear()
    }

    println("Final PASS: count actives = ${actives.count()}")
}

private fun keepActive(pos: Pos4D, actives: MutableSet<Pos4D>): Boolean {
    val activeNeighbors = pos.neighbors().count(actives::contains)
    return activeNeighbors == 2 || activeNeighbors == 3
}

private fun shouldActivate(pos: Pos4D, actives: MutableSet<Pos4D>): Boolean {
    val activeNeighbors = pos.neighbors().count(actives::contains)
    return activeNeighbors == 3
}

data class Pos4D(val x: Int, val y: Int, val z: Int, val w: Int) {
    fun neighbors() = IntRange(-1, 1).map { x ->
        IntRange(-1, 1).map { y ->
            IntRange(-1, 1).map { z ->
                IntRange(-1, 1).map { w ->
                    Pos4D(this.x + x, this.y + y, this.z + z, this.w + w)
                }
            }.flatten()
        }.flatten()
    }.flatten()
        .filter { it != this }
}