fun main() {
    fun part1(input: List<String>): Int {
        val loadedConfiguration = Configuration(
            red = 12,
            green = 13,
            blue = 14,
        )

        val possibleGames = input.possibleGamesGiven(loadedConfiguration)

        return possibleGames.sumOf { game ->
            val matchResult = "Game (\\d+):".toRegex().find(game)!!
            matchResult.groupValues[1].toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

data class Configuration(val red: Int, val green: Int, val blue: Int)

fun List<String>.possibleGamesGiven(configuration: Configuration): List<String> {
    return filter { game -> game.isPossibleGiven(configuration) }
}

fun String.isPossibleGiven(configuration: Configuration): Boolean {
    val reds = "(\\d+) red".toRegex().findAll(this)
        .toList()
        .map { it.groupValues[1].toInt() }

    val greens = "(\\d+) green".toRegex().findAll(this)
        .toList()
        .map { it.groupValues[1].toInt() }

    val blues = "(\\d+) blue".toRegex().findAll(this)
        .toList()
        .map { it.groupValues[1].toInt() }

    if (reds.any { it > configuration.red }) return false
    if (greens.any { it > configuration.green }) return false
    if (blues.any { it > configuration.blue }) return false

    return true
}