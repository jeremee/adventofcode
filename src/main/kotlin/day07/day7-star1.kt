import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()
    val colorBagRegex = Regex("[0-9]+ ([a-z ]+) bag[s]*[.,]{1}")

    val map = mutableMapOf<String, List<String>>()
    inputs.forEach { input ->
        val (colorBagContaining, containedColorBags) = input.split(" bags contain ")
        val bags = colorBagRegex.findAll(containedColorBags)
        map.put(colorBagContaining, bags.map {
            val (bag) = it.destructured
            bag
        }.toList())
    }

    val searchedBag = "shiny gold"
    val result = map.keys
        .filterNot { it == searchedBag }
        .count { containerBag ->
            doesContainColorBag(searchedBag, containerBag, map)
        }

    println("$result bags")
}

fun doesContainColorBag(searchedBag: String, containerBag: Any, bags: Map<String, List<String>>): Boolean {
    if (searchedBag == containerBag) return true

    return bags[containerBag]?.any { containedBag ->
        doesContainColorBag(searchedBag, containedBag, bags)
    } ?: false
}
