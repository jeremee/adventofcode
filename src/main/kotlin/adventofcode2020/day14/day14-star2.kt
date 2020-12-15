import java.io.File
import kotlin.math.pow

private val regexpMask = Regex("mask = ([X10]+)")
private val regexpMem = Regex("mem\\[([0-9]+)\\] = ([0-9]+)")

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()

    lateinit var mask: Mask2
    val values = mutableMapOf<Long, Long>()
    inputs.forEach { line ->
        when {
            regexpMask.matches(line) -> mask = extractMask(line)
            regexpMem.matches(line) -> setValueWithMask(line, values, mask)
        }
    }
    println(values.values.sum())
}

private fun extractMask(line: String): Mask2 {
    val (maskString) = regexpMask.find(line)!!.destructured
    val or = java.lang.Long.parseUnsignedLong(maskString.replace('X', '0'), 2)
    val floating = maskString.replace('1', '0')

    return Mask2(or, floating)
}

private fun setValueWithMask(line: String, values: MutableMap<Long, Long>, mask: Mask2) {
    val (indiceString, valueString) = regexpMem.find(line)!!.destructured
    val indice = indiceString.toLong()
    val value = valueString.toLong()

    val indiceMasked = java.lang.Long.toBinaryString(indice or mask.or).padStart(mask.floating.length, '0')
    val floatingMask = mask.floating
    val numX = floatingMask.count { it == 'X' }

    for (i in 0..2.0.pow(numX).toLong()) {
        val floatingValues = java.lang.Long.toBinaryString(i).padStart(numX, '0')
        var floatingValuesIndex = 0
        val indiceFloated = StringBuilder(indiceMasked)
        floatingMask.forEachIndexed { j, c ->
            if (c == 'X') {
                indiceFloated[j] = floatingValues[floatingValuesIndex]
                floatingValuesIndex++
            }
        }

        val indiceToWrite = java.lang.Long.parseUnsignedLong(indiceFloated.toString(), 2)
        values[indiceToWrite] = value
    }
}

private data class Mask2(
    val or: Long,
    val floating: String
)
