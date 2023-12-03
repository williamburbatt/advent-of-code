/**
 * Enter problem description here
 *
 *
 */
fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputFiles/Day01_test")
    check(part1(testInput) == 1)

    val input = readInput("inputFiles/Day01")
    part1(input).println()
    part2(input).println()
}