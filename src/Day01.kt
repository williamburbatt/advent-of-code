fun main() {
    fun part1(input: List<String>): Int {
        var sumOfAllDigits = 0
        input.forEach { item ->
            val filterAllDigits = item.filter { it.isDigit() }
            val first = filterAllDigits.first().toString()
            val second = filterAllDigits.last().toString()
            val result = (first+second).toInt()
            sumOfAllDigits += result
        }
        return sumOfAllDigits
    }



    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("inputFiles/Day01_test")
    val input = readInput("inputFiles/Day01")
    println("This is Test Data:")
    part1(testInput).println()
    println("This is the actual input: ")
    part1(input).println()
}
