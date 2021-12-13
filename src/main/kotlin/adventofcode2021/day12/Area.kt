package adventofcode2021.day12

sealed class Area(open val name: String) {
    private val destArea = mutableListOf<Area>()
    open fun getAreas(): List<Area> = destArea
    fun add(area: Area) {
        if (destArea.none { it.name == area.name } && area != Start)
            destArea.add(area)
    }

    companion object {
        fun create(name: String) =
                if (name == "start") Start
                else if (name == "end") End
                else if (name.toUpperCase() == name) BigCave(name)
                else SmallCave(name)
    }

    object Start : Area("start")

    object End : Area("end") {
        override fun getAreas() = emptyList<Area>()
    }

    data class BigCave(override val name: String) : Area(name)

    data class SmallCave(override val name: String) : Area(name)
}