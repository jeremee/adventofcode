import java.io.File

fun main() {
    val inputs = File("src/main/kotlin/day7/data").readLines()
    val colorBagRegex = Regex("([0-9]+) ([a-z ]+) bag[s]*[.,]{1}")

    val map = mutableMapOf<String, List<Pair<String, Int>>>()
    inputs.forEach { input ->
        val (colorBagContaining, containedColorBags) = input.split(" bags contain ")
        val bags = colorBagRegex.findAll(containedColorBags)
        map.put(colorBagContaining, bags.map {
            val (number, bag) = it.destructured
            bag to number.toInt()
        }.toList())
    }

    val innerBags = countInnerBags("shiny gold", map)
    println("$innerBags bags")
}

fun countInnerBags(containerBag: Any, bags: Map<String, List<Pair<String, Int>>>): Int =
    bags[containerBag]!!.sumBy { (bag, number) ->
        number * (countInnerBags(bag, bags) + 1)
    }