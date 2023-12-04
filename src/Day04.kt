import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        val cards = input.map(Card.Companion::parse)

        return cards.sumOf { it.points }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

data class Card(
    val cardNumber: Int,
    val ownNumbers: List<Int>,
    val winningNumbers: List<Int>,
) {
    private val ownWinningNumbers: Set<Int> by lazy {
        ownNumbers.intersect(winningNumbers.toSet())
    }

    val points: Int by lazy {
        if (ownWinningNumbers.isEmpty()) return@lazy 0

        2.0.pow(ownWinningNumbers.count() - 1).toInt()
    }

    companion object {
        fun parse(line: String): Card {
            val matchResult = "Card +(\\d+): (.+) \\| (.+)".toRegex()
                .find(line) ?: error("Invalid line: $line")

            val cardNumber = matchResult.groupValues[1].toInt()
            val winningNumbers = parseNumbers(matchResult.groupValues[2])
            val ownNumbers = parseNumbers(matchResult.groupValues[3])

            return Card(
                cardNumber = cardNumber,
                ownNumbers = ownNumbers,
                winningNumbers = winningNumbers,
            )
        }

        private fun parseNumbers(line: String): List<Int> {
            return "(\\d+)".toRegex()
                .findAll(line).toList()
                .map { it.value.toInt() }
        }
    }
}