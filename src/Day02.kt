val possibleIDList = ArrayList<Int>()
val allPowers = ArrayList<Int>()

fun main() {
    fun part1(input: List<String>): Int {

        input.forEach { singleInputString ->
            val inputSplit = singleInputString.split(":")
            val gameNumber = inputSplit[0].replace("Game ","").toInt()
            possibleIDList.add(gameNumber)

            val fullGame = inputSplit[1].trim()
            val games = fullGame.split(";")

            handleInvalidGames(games, gameNumber)
        }
        possibleIDList.println()
        return possibleIDList.sum()
    }

    fun part2(input: List<String>): Int {
        input.forEach { singleInputString ->
            val inputSplit = singleInputString.split(":")
            val gameNumber = inputSplit[0].replace("Game ","").toInt()
            possibleIDList.add(gameNumber)

            val fullGame = inputSplit[1].trim()
            val games = fullGame.split(";")
            handlePowerGame(games)
        }
        allPowers.println()
        return allPowers.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputFiles/Day02_test")
    //check(part1(testInput) == 1)

    //val input = readInput("Day01")
    part2(testInput).println()
    //part2(input).println()
}
fun handleInvalidGames(games: List<String>, gameNumber: Int){
    val MAX_RED = 12
    val MAX_GREEN = 13
    val MAX_BLUE = 14

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

fun handlePowerGame(games: List<String>){
    var MIN_RED = 0
    var MIN_GREEN = 0
    var MIN_BLUE = 0
    for(game in games){
        val gameSplit = game.trim().split(",")
        gameSplit.println()
        gameSplit.forEach { singleCube ->
            when {
                singleCube.contains("red") -> {
                    val redNum = singleCube.replace(" red","").trim()
                    if (redNum.toInt() > MIN_RED){
                        MIN_RED = redNum.toInt()
                    }
                }
                singleCube.contains("green") -> {
                    val greenNum = singleCube.replace(" green","").trim()
                    if (greenNum.toInt() > MIN_GREEN){
                        MIN_GREEN = greenNum.toInt()
                    }
                }
                singleCube.contains("blue") -> {
                    val blueNum = singleCube.replace(" blue","").trim()
                    if (blueNum.toInt() > MIN_BLUE){
                        MIN_BLUE = blueNum.toInt()
                    }
                }
            }
        }
    }
    allPowers.add(MIN_RED*MIN_BLUE*MIN_GREEN)
}

