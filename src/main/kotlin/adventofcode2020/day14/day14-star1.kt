import java.io.File

private val regexpMask = Regex("mask = ([X10]+)")
private val regexpMem = Regex("mem\\[([0-9]+)\\] = ([0-9]+)")

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()

    lateinit var mask: Mask1
    val values = mutableMapOf<Int, Long>()
    inputs.forEach { line ->
        when {
            regexpMask.matches(line) -> mask = extractMask(line)
            regexpMem.matches(line) -> setValueWithMask(line, values, mask)
        }
    }
    println(values.values.sum())
}

private fun extractMask(line: String): Mask1 {
    val (maskString) = regexpMask.find(line)!!.destructured
    val or = java.lang.Long.parseUnsignedLong(maskString.replace('X', '0'), 2)
    val and = java.lang.Long.parseUnsignedLong(maskString.replace('X', '1'), 2)

    return Mask1(or, and)
}

private fun setValueWithMask(line: String, values: MutableMap<Int, Long>, mask: Mask1) {
    val (indiceString, valueString) = regexpMem.find(line)!!.destructured
    val indice = indiceString.toInt()
    val value = valueString.toLong()

    values[indice] = value or mask.or and mask.and
}

private data class Mask1(
    val or: Long,
    val and: Long
)
