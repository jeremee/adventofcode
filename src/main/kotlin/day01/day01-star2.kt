import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()

    val ints = inputs.map(String::toInt)
    for (i in ints.indices) {
        for (j in i + 1 until ints.size) {
            for (k in j + 1 until ints.size) {
                if (ints[i] + ints[j] + ints[k] == 2020) {
                    println(ints[i] * ints[j] * ints[k])
                    return
                }
            }
        }
    }
}