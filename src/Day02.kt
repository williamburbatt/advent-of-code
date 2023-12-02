fun main() {
    fun part1(input: List<String>): Int {
        val MAX_RED = 12
        val MAX_GREEN = 13
        val MAX_BLUE = 14
        val possibleIDList = ArrayList<Int>()
        input.forEach { singleInputString ->
            val inputSplit = singleInputString.split(":")
            val gameNumber = inputSplit[0].replace("Game ","").toInt()
            possibleIDList.add(gameNumber)
            println("Game: $gameNumber")
            val fullGame = inputSplit[1].trim()
            val games = fullGame.split(";")
            for(game in games){
                val gameSplit = game.trim().split(",")
                gameSplit.println()
                gameSplit.forEach { singleCube ->
                    when {
                        singleCube.contains("red") -> {
                            val redNum = singleCube.replace(" red","").trim()
                            if (redNum.toInt() > MAX_RED){
                                possibleIDList.remove(gameNumber)
                            }
                        }
                        singleCube.contains("green") -> {
                            val greenNum = singleCube.replace(" green","").trim()
                            if (greenNum.toInt() > MAX_GREEN){
                                possibleIDList.remove(gameNumber)
                            }
                        }
                        singleCube.contains("blue") -> {
                            val blueNum = singleCube.replace(" blue","").trim()
                            if (blueNum.toInt() > MAX_BLUE){
                                possibleIDList.remove(gameNumber)
                            }
                        }
                    }
                }
            }

        }
        possibleIDList.println()
        return possibleIDList.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputFiles/Day02_test")
    //check(part1(testInput) == 1)

    //val input = readInput("Day01")
    part1(testInput).println()
    //part2(input).println()
}
