import java.io.File

fun main() {
    val inputs = File("src/main/kotlin/day4/data").readLines()

    var validPassportCount = 0
    val fieldsFound = mutableSetOf<String>()

    inputs.forEach { line ->
        if (line.isEmpty() || line.isBlank()) {
            if (containAllFields(fieldsFound)) {
                validPassportCount++
            }
            fieldsFound.clear()
        } else {
            line.split(" ")
                .forEach { keyValue ->
                    val (field, value) = keyValue.split(":")
                    if (Fields.validate(field, value)) {
                        fieldsFound.add(field)
                    }
                }
        }
    }

    if (containAllFields(fieldsFound)) {
        validPassportCount++
    }

    println(validPassportCount)
}

private fun containAllFields(fields: Set<String>): Boolean =
    fields.containsAll(Fields.values().map(Fields::name))

private enum class Fields(private val validator: (String) -> Boolean) {
    byr(validNumber(1920, 2002)),
    iyr(validNumber(2010, 2020)),
    eyr(validNumber(2020, 2030)),
    hgt(validHeight()),
    hcl(validHexColor()),
    ecl(validStringColor()),
    pid(validDigits(9));
    // cid (Country ID) - ignored, missing or not.

    companion object {
        fun validate(fieldName: String, fieldValue: String): Boolean = try {
            valueOf(fieldName).validate(fieldValue)
        } catch (e: Exception) {
            false
        }
    }

    fun validate(value: String): Boolean = validator(value)
}

private fun validNumber(min: Int, max: Int): (String) -> Boolean = {
    try {
        it.toInt().let { number ->
            number >= min && number <= max
        }
    } catch (e: Exception) {
        false
    }
}

private fun validHeight(): (String) -> Boolean = {
    when {
        it.endsWith("cm") -> validNumber(150, 193)(it.dropLast(2))
        it.endsWith("in") -> validNumber(59, 76)(it.dropLast(2))
        else -> false
    }
}

private fun validHexColor(): (String) -> Boolean = {
    Regex("#[0-9[a-f]]{6}").matches(it)
}

private fun validStringColor(): (String) -> Boolean = {
    listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(it)
}

private fun validDigits(nbDigits: Int): (String) -> Boolean = {
    Regex("[0-9]{$nbDigits}").matches(it)
}