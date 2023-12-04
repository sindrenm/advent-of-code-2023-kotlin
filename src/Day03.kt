fun main() {
    fun part1(input: List<String>): Int {
        val partNumbers = mutableListOf<Int>()

        input.mapIndexed { index, line ->
            val previousLine = if (index == 0) null else input[index - 1]
            val nextLine = if (index >= input.size - 1) null else input[index + 1]

            val allNumbersOnLine = "(\\d+)".toRegex()
                .findAll(line).toList()
                .flatMap { it.groups }
                .filterNotNull()
                .distinct()

            allNumbersOnLine.forEach { matchGroup ->
                if (matchGroup.range.isNextToSymbol(line, previousLine, nextLine)) {
                    partNumbers.add(matchGroup.value.toInt())
                }
            }

            null
        }.filterNotNull()

        return partNumbers.sum()
    }

    fun part2(input: List<String>): Int {
        val gearRatios = mutableListOf<Int>()

        input.mapIndexed { index, line ->
            val previousLine = if (index == 0) null else input[index - 1]
            val nextLine = if (index >= input.size - 1) null else input[index + 1]

            val asteriskIndices = "(\\*)".toRegex()
                .findAll(line).toList()
                .map { it.range.first }

            val summed = asteriskIndices.mapNotNull {
                it.gearRatioOrNull(line, previousLine, nextLine)
            }.sum()

            gearRatios.add(summed)
        }

        return gearRatios.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

fun IntRange.isNextToSymbol(thisLine: String, previousLine: String?, nextLine: String?): Boolean {
    val lineLength = thisLine.length
    val startIndex = first.minus(1).coerceAtLeast(0)
    val endIndex = last.plus(1).coerceAtMost(lineLength - 1)

    val isNextToSymbolOnThisLine = listOf(thisLine[startIndex], thisLine[endIndex])
        .any { it.isSymbol() }

    val isNextToSymbolOnPreviousLine = previousLine
        ?.substring(IntRange(startIndex, endIndex))
        ?.any { it.isSymbol() }
        ?: false

    val isNextToSymbolOnNextLine = nextLine
        ?.substring(IntRange(startIndex, endIndex))
        ?.any { it.isSymbol() }
        ?: false

    return isNextToSymbolOnPreviousLine ||
            isNextToSymbolOnThisLine ||
            isNextToSymbolOnNextLine
}

fun Char.isSymbol(): Boolean {
    if (equals('.')) return false
    if (isDigit()) return false

    return true
}

fun Int.gearRatioOrNull(thisLine: String, previousLine: String?, nextLine: String?): Int? {
    val startIndex = minus(1).coerceAtLeast(0)
    val endIndex = plus(1).coerceAtMost(thisLine.length - 1)
    val requiredRange = IntRange(startIndex, endIndex)

    val allAdjacent = listOfNotNull(
        previousLine.numbersInRange(requiredRange),
        thisLine.numbersInRange(requiredRange),
        nextLine.numbersInRange(requiredRange),
    ).flatten()

    if (allAdjacent.count() < 2) return null

    return allAdjacent.reduce(Int::times)
}

fun String?.numbersInRange(range: IntRange): List<Int>? {
    if (this.isNullOrBlank()) return null

    return this
        .let(numberRegex::findAll).toList()
        .flatMap { it.groups }
        .filterNotNull()
        .filter { it.range in range }
        .distinct()
        .map { it.value.toInt() }
        .takeUnless { it.isEmpty() }
}

operator fun IntRange.contains(other: IntRange): Boolean {
    if (other.first in this) return true
    if (other.last in this) return true

    return false
}

val numberRegex = "(\\d+)".toRegex()
