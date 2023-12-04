fun main() {
    fun part1(input: List<String>): Int {
        return input
            .sumOf { line ->
                val firstDigit = line.first { it.isDigit() }
                val lastDigit = line.last { it.isDigit() }

                "$firstDigit$lastDigit".toInt()
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.stringDigitToDigit() }
            .sumOf { line ->
                val firstDigit = line.first { it.isDigit() }
                val lastDigit = line.last { it.isDigit() }

                "$firstDigit$lastDigit".toInt()
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun String.stringDigitToDigit(): String {
    return replace(stringDigitsMatcher.toRegex()) { matchResult ->
        stringDigits[matchResult.value].toString()
    }
}

val stringDigits = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
)

val stringDigitsMatcher = buildString {
    append("(")
    append(stringDigits.keys.joinToString("|"))
    append(")")
}