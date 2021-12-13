import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()
    val width = inputs[0].length
    val speedsX = arrayOf(1, 3, 5, 7, width + 1)
    val speedsY = arrayOf(1, 1, 1, 1, 2)

    val result = (speedsX.indices)
        .map { i ->
            println(countTrees(inputs, speedsX[i], speedsY[i]))

            countTrees(inputs, speedsX[i], speedsY[i])
        }.reduce { acc, i -> acc * i }

    println(result)
}

private fun countTrees(inputs: List<String>, speedX: Int, speedY: Int): Long {
    val length = inputs[0].length

    return (inputs.indices step speedY)
        .asSequence()
        .filterIndexed { x, y ->
            val pos = (speedX * x) % length
            inputs[y][pos] == '#'
        }.count().toLong()
}