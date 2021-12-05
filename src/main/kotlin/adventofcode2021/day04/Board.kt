package adventofcode2021.day04

data class Board(val numbersByCol: List<List<Int>>,
                 val numbersByRow: List<List<Int>>) {

    companion object {
        private fun <T> List<List<T>>.transpose(): List<List<T>> {
            val n = this.size - 1
            val m = this.first().size - 1

            return (0..n).map { x ->
                (0..m).map { y ->
                    this[y][x]
                }
            }
        }
    }

    constructor(inputs: List<List<Int>>) : this(inputs, inputs.transpose())

    fun isWinning(winningNumbers: List<Int>) =
            numbersByCol.any { winningNumbers.containsAll(it) }
                    || numbersByRow.any { winningNumbers.containsAll(it) }
}