package adventofcode2021.day03

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines().map { it.toCharArray() }

    val oxygenValues = inputs.toMutableList()
    var i = 0
    while (oxygenValues.size > 1) {
        val count0 = oxygenValues.count { it[i] == '0' }
        val count1 = oxygenValues.count { it[i] == '1' }
        if (count0 > count1)
            oxygenValues.removeAll { it[i] == '1' }
        else if (count0 <= count1)
            oxygenValues.removeAll { it[i] == '0' }
        i++
    }
    val oxygenValue = oxygenValues.first().joinToString("").toInt(2)

    i = 0
    val co2Values = inputs.toMutableList()
    while (co2Values.size > 1) {
        val count0 = co2Values.count { it[i] == '0' }
        val count1 = co2Values.count { it[i] == '1' }
        if (count0 > count1)
            co2Values.removeAll { it[i] == '0' }
        else if (count0 <= count1)
            co2Values.removeAll { it[i] == '1' }
        i++
    }

    val co2Value = co2Values.first().joinToString("").toInt(2)

    println(oxygenValue * co2Value)
}