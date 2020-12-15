fun main(vararg args: String) {
    val inputs = "7,14,0,17,11,1,2"
        .split(",")
        .map(String::toInt)

    val numbers = mutableMapOf<Int, Int>()
    inputs
        .dropLast(1)
        .forEachIndexed { i, number ->
            numbers[number] = i + 1
        }

    var lastSpokenNumber = inputs.last()

    for (i in inputs.size until 30000000) {
        if (numbers.containsKey(lastSpokenNumber)) {
            val lastSpokenNumberPosition = numbers[lastSpokenNumber]!!
            numbers[lastSpokenNumber] = i
            lastSpokenNumber = i - lastSpokenNumberPosition
        } else {
            numbers[lastSpokenNumber] = i
            lastSpokenNumber = 0
        }
    }

    println(lastSpokenNumber)
}
