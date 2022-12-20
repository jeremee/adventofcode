package adventofcode2022.day09

enum class Direction(private val moveX: Int, private val moveY: Int) {
    U(0, 1),
    D(0, -1),
    L(-1, 0),
    R(1, 0);

    fun move(p: Pos) = Pos(p.first + moveX, p.second + moveY)
}