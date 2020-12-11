import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()
    val values = inputs.map(String::toInt)
        .sorted()
        .toMutableList()
    values.add(values.last() + 3)

    var step1Count = 0
    var step3Count = 0
    var currentStep = 0
    for (i in 1..values.last()) {
        currentStep++
        if (values.contains(i)) {
            when (currentStep) {
                1 -> step1Count++
                3 -> step3Count++
                else -> throw IllegalStateException("Step of width $currentStep is not handled")
            }
            currentStep = 0
        }
    }

    println(step1Count * step3Count)
}
