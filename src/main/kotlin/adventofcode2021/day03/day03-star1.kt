package adventofcode2021.day03

import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines().map { it.toCharArray() }

    val count0 = MutableList(inputs.first().size, { 0 })
    val count1 = MutableList(inputs.first().size, { 0 })

    inputs.forEach {
        it.forEachIndexed { i, value ->
            if (value == '0')
                count0[i] = count0[i] + 1
            else if (value == '1')
                count1[i] = count1[i] + 1
        }
    }

    val max = count0.mapIndexed { i, value ->
        if (count0[i] > count1[i]) "0" else "1"
    }
            .joinToString("")
            .toInt(2)

    val min = count0.mapIndexed { i, value ->
        if (count0[i] < count1[i]) "0" else "1"
    }
            .joinToString("")
            .toInt(2)

    println(min * max)
}