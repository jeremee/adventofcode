package adventofcode2021.day12

import java.io.File

fun main(vararg args: String) {
    val areas = mutableListOf<Area>()

    File("data").readLines().forEach {
        val (area1Name, area2Name) = it.split("-")
        val area1 = areas.firstOrNull { it.name == area1Name } ?: Area.create(area1Name).also { areas.add(it) }
        val area2 = areas.firstOrNull { it.name == area2Name } ?: Area.create(area2Name).also { areas.add(it) }

        area1.add(area2)
        area2.add(area1)
    }

    val pathes = buildPathes(Area.Start, areas, emptyList())

    println(pathes.size)
}

private fun buildPathes(currentArea: Area, areas: List<Area>, currentPath: List<Area>): List<List<Area>> {
    if (currentArea == Area.End) return listOf(currentPath)

    return currentArea.getAreas()
            .filter { it !is Area.SmallCave || !currentPath.contains(it) }
            .map { buildPathes(it, areas, currentPath + it) }
            .flatten()
}
