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
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

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