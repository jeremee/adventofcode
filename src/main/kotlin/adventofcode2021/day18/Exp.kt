package adventofcode2021.day18

sealed class Exp {
    companion object {
        fun fromString(op: String) = readExp(op)

        private fun readExp(op: String): Exp {
            when {
                op[0] == '[' -> {
                    return Op(readExp(getFirstExp(op)), readExp(getSecondExp(op)))
                }
                else -> {
                    return Val(op.toInt())
                }
            }
        }

        private fun getFirstExp(op: String) = op.substring(1, getCommaIndex(op))
        private fun getSecondExp(op: String) = op.substring(getCommaIndex(op) + 1, op.length - 1)

        private fun getCommaIndex(op: String): Int {
            var i = 0
            var count = 0
            while (true) {
                when (op[i]) {
                    '[' -> count++
                    ']' -> count--
                    ',' -> if (count == 1) return i
                }
                i++
            }
        }
    }

    abstract fun getValue(): Int
    abstract fun print(): String

    data class Op(var exp1: Exp, var exp2: Exp) : Exp() {
        override fun getValue(): Int = exp1.getValue() * 3 + exp2.getValue() * 2
        override fun print(): String = "[${exp1.print()},${exp2.print()}]"
    }

    data class Val(val exp: Int) : Exp() {
        override fun getValue() = exp
        override fun print(): String = exp.toString()
    }

}