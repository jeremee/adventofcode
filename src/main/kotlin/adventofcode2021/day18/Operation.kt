package adventofcode2021.day18

import kotlin.math.ceil
import kotlin.math.floor

object Operation {
    fun add(exp1: String, exp2: String) = reduce("[$exp1,$exp2]")

    private fun reduce(opToReduce: String): String {
        var op = opToReduce
        while (true) {

            var newOp = explode(op)
            if (newOp != op) {
                op = newOp
                continue
            }

            newOp = split(op)
            if (newOp != op) {
                op = newOp
                continue
            }

            break
        }
        return op
    }

    private fun split(op: String): String {
        var i = 0
        while (i < op.length) {
            when (op[i]) {
                '[', ']', ',' -> i++
                else -> {
                    val value = readValue(op.substring(i))
                    if (value.toInt() >= 10) {
                        val mid = value.toInt().toDouble() / 2
                        return op.replaceRange(i, i + value.length, "[${floor(mid).toInt()},${ceil(mid).toInt()}]")
                    }
                    i++
                }
            }
        }
        return op
    }

    private fun explode(op: String): String {
        var i = 0
        var countDeepness = 0
        while (i < op.length) {
            when (op[i]) {
                '[' -> {
                    countDeepness++
                    if (countDeepness == 5) {
                        val val1AsString = readValue(op.substring(i + 1))
                        val val1 = val1AsString.toInt()
                        val val2AsString = readValue(op.substring(i + 1 + val1AsString.length + 1))
                        val val2 = val2AsString.toInt()
                        var newOp = op

                        if (newOp.substring(i + 3 + val1AsString.length + val2AsString.length).any { it !in listOf('[', ',', ']') }) { // has value to right
                            val nextValueIndex = i + getClosingIndex(newOp.substring(i)) + newOp.substring(i + getClosingIndex(newOp.substring(i))).indexOfFirst { it !in listOf('[', ',', ']') }
                            val valueAsString = readValue(newOp.substring(nextValueIndex))
                            val value = valueAsString.toInt()
                            newOp = newOp.replaceRange(nextValueIndex, nextValueIndex + valueAsString.length, (value + val2).toString())
                        }

                        newOp = newOp.replaceRange(i, i + 3 + val1AsString.length + val2AsString.length, "0")

                        if (newOp.substring(0, i).any { it !in listOf('[', ',', ']') }) { // has value to left
                            val previousValueIndexEnd = newOp.substring(0, i).indexOfLast { it !in listOf('[', ',', ']') }
                            val valueAsString = readValueReverse(newOp.substring(0, previousValueIndexEnd + 1))
                            val value = valueAsString.toInt()
                            newOp = newOp.replaceRange(previousValueIndexEnd - valueAsString.length + 1, previousValueIndexEnd + 1, (value + val1).toString())
                        }

                        return newOp
                    }
                }
                ']' -> countDeepness--

            }
            i++
        }
        return op
    }

    private fun split(op: Exp.Op): Boolean {
        val hasSplit1 = if (op.exp1 is Exp.Val && op.exp1.getValue() >= 10) {
            val mid = op.exp1.getValue().toDouble() / 2
            op.exp1 = Exp.Op(Exp.Val(floor(mid).toInt()), Exp.Val(ceil(mid).toInt()))
            true
        } else if (op.exp1 is Exp.Op) {
            split(op.exp1 as Exp.Op)
        } else false

        val hasSplit2 = if (op.exp2 is Exp.Val && op.exp2.getValue() >= 10) {
            val mid = op.exp2.getValue().toDouble() / 2
            op.exp2 = Exp.Op(Exp.Val(floor(mid).toInt()), Exp.Val(ceil(mid).toInt()))
            true
        } else if (op.exp2 is Exp.Op) {
            split(op.exp2 as Exp.Op)
        } else false

        return hasSplit1 || hasSplit2
    }

    private fun readValue(op: String): String {
        val i = op.indexOfFirst { it in listOf('[', ',', ']') }
        return op.substring(0, i)
    }

    private fun readValueReverse(op: String): String {
        val i = op.indexOfLast { it in listOf('[', ',', ']') }
        return op.substring(i + 1, op.lastIndex + 1)
    }

    private fun getClosingIndex(op: String): Int {
        var i = 0
        var count = 0
        while (true) {
            when (op[i]) {
                '[' -> count++
                ']' -> count--
            }
            if (count == 0) return i
            i++
        }
    }
}