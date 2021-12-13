import adventofcode2020.day16.Criterion
import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()

    val criteriaRegexp = Regex("(.*): ([0-9]+-[0-9]+) or ([0-9]+-[0-9]+)")
    val criteria = mutableListOf<Criterion>()
    for (i in inputs.indices) {
        if (inputs[i].isBlank()) break
        val (name, range1, range2) = criteriaRegexp.find(inputs[i])!!.destructured
        criteria.add(Criterion(name, rangeStringToIntRange(range1), rangeStringToIntRange(range2)))
    }

    val myTicketIndex = inputs.indexOf("your ticket:") + 1
    val myTicket = inputs[myTicketIndex].toTicket()
    val tickets = mutableListOf(myTicket)
    val nearbyTicketsIndex = inputs.indexOf("nearby tickets:") + 1
    for (i in nearbyTicketsIndex until inputs.size) {
        val ticket = inputs[i].toTicket()
        if (ticket.none { value -> criteria.none { it.doesRespectCriteria(value) } })
            tickets.add(ticket)
    }
    val fieldCount = myTicket.size

    val possibleCriteriaByField =
        IntRange(0, fieldCount - 1).map { mutableListOf<Criterion>().apply { addAll(criteria) } }

    var passCounter = 0
    do {
        println("PASS $passCounter: ${possibleCriteriaByField.flatten().count()} possibilities for $fieldCount fields")

        possibleCriteriaByField.forEachIndexed { fieldNumber, possibleCriteria ->
            println("FIELD $fieldNumber: ${possibleCriteria.map(Criterion::name).joinToString(", ")}")
            val impossibleCriteriaOnField = possibleCriteria.filter { criteria ->
                hasAnyTicketThatDoesNotMatchOnField(tickets, criteria, fieldNumber)
            }
            possibleCriteria.removeAll(impossibleCriteriaOnField)
            if (possibleCriteria.size == 1) {
                removeCriteriaFromPossibilities(possibleCriteriaByField, possibleCriteria.first(), fieldNumber)
            }
        }

        passCounter++
    } while (possibleCriteriaByField.any { it.size != 1 })

    var result = 1L
    possibleCriteriaByField.forEachIndexed { index, criteria ->
        if (criteria.first().name.startsWith("departure")) result *= myTicket[index]
    }
    println("END after $passCounter PASS, result = $result")
}

private fun String.toTicket() = this.split(',').map(String::toInt)

private fun rangeStringToIntRange(range: String) =
    range
        .split('-')
        .map(String::toInt)
        .let { IntRange(it[0], it[1]) }

private fun hasAnyTicketThatDoesNotMatchOnField(
    tickets: MutableList<List<Int>>,
    criterion: Criterion,
    fieldNumber: Int
) =
    tickets.any { ticket ->
        !criterion.doesRespectCriteria(ticket[fieldNumber])
    }

private fun removeCriteriaFromPossibilities(
    possibilities: List<MutableList<Criterion>>,
    criterion: Criterion,
    exceptFieldNumber: Int
) {
    possibilities
        .filterIndexed { fieldNumber, _ -> fieldNumber != exceptFieldNumber }
        .forEach { criteria -> criteria.remove(criterion) }
}