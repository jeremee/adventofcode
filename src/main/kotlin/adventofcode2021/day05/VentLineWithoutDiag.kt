package adventofcode2021.day05

data class VentLineWithoutDiag(
        val x1: Int,
        val y1: Int,
        val x2: Int,
        val y2: Int
) {
    constructor(p1: Pos, p2: Pos) : this(p1.x, p1.y, p2.x, p2.y)

    fun getPos() =
            if (x1 == x2)
                (if (y1 < y2) (y1..y2) else (y2..y1)).map { y -> Pos(x1, y) }
            else if (y1 == y2)
                (if (x1 < x2) (x1..x2) else (x2..x1)).map { x -> Pos(x, y1) }
            else emptyList()
}