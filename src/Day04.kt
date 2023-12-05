import java.util.Stack
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        val cards = input.map(Card.Companion::parse)

        return cards.sumOf { it.points }
    }

    fun part2(input: List<String>): Int {
        val originalCards = input.map(Card.Companion::parse).reversed()
        val getCardsStack = Stack<Card>()

        originalCards.forEach { getCardsStack.push(it) }

        var counter = 0
        while (getCardsStack.any()) {
            val currentCard = getCardsStack.pop()

            val winCount = currentCard.ownWinningNumbers.count()

            val cardsWeWonFromCurrentCard = List(winCount) { index ->
                originalCards
                    .firstOrNull { it.cardNumber == currentCard.cardNumber + index + 1 }
                    ?.copy(copyOfCardNumber = currentCard.cardNumber, isCopied = false)
            }.filterNotNull()

            cardsWeWonFromCurrentCard.reversed().forEach { getCardsStack.push(it) }

            counter++
        }

        println("counter = $counter")

        return counter
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

data class Card(
    val cardNumber: Int,
    val ownNumbers: List<Int>,
    val winningNumbers: List<Int>,
    val copyOfCardNumber: Int?,
    var isCopied: Boolean,
) {
    val ownWinningNumbers: Set<Int> by lazy {
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
                copyOfCardNumber = null,
                isCopied = false,
            )
        }

        private fun parseNumbers(line: String): List<Int> {
            return "(\\d+)".toRegex()
                .findAll(line).toList()
                .map { it.value.toInt() }
        }
    }
}