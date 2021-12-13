import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()
    val values = inputs.map(String::toInt)

    for (i in values.indices) {
        for (j in i + 1 until values.size) {
            if (values[i] + values[j] == 2020) {
                println(values[i] * values[j])
                return
            }
        }
    }
}