import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()

    val busWaitTimestamp = inputs[0].toInt()
    val busIds = inputs[1]
        .split(",")
        .asSequence()
        .filter { it != "x" }
        .map(String::toInt)

    val busId = getNextBus(busWaitTimestamp, busIds)

    println(busId * getBusWaitingTime(busWaitTimestamp, busId))
}

private fun getNextBus(busWaitTimestamp: Int, busIds: Sequence<Int>) =
    busIds.minBy { getBusWaitingTime(busWaitTimestamp, it) }
        ?: throw IllegalArgumentException("Need at least one bus id")

private fun getBusWaitingTime(busWaitTimestamp: Int, busId: Int) =
    busId - (busWaitTimestamp % busId)