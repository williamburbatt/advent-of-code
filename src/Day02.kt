/**
 * --- Day 2: Cube Conundrum ---
 * You're launched high into the atmosphere! The apex of your trajectory just barely reaches the surface of a large island floating in the sky. You gently land in a fluffy pile of leaves. It's quite cold, but you don't see much snow. An Elf runs over to greet you.
 *
 * The Elf explains that you've arrived at Snow Island and apologizes for the lack of snow. He'll be happy to explain the situation, but it's a bit of a walk, so you have some time. They don't get many visitors up here; would you like to play a game in the meantime?
 *
 * As you walk, the Elf shows you a small bag and some cubes which are either red, green, or blue. Each time you play this game, he will hide a secret number of cubes of each color in the bag, and your goal is to figure out information about the number of cubes.
 *
 * To get information, once a bag has been loaded with cubes, the Elf will reach into the bag, grab a handful of random cubes, show them to you, and then put them back in the bag. He'll do this a few times per game.
 *
 * You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).
 *
 * For example, the record of a few games might look like this:
 *
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 * In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.
 *
 * The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
 *
 * In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with that configuration. However, game 3 would have been impossible because at one point the Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.
 *
 * Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
 *
 *
 * --- Part Two ---
 * The Elf says they've stopped producing snow because they aren't getting any water! He isn't sure why the water stopped; however, he can show you how to get to the water source to check it out for yourself. It's just up ahead!
 *
 * As you continue your walk, the Elf poses a second question: in each game you played, what is the fewest number of cubes of each color that could have been in the bag to make the game possible?
 *
 * Again consider the example games from earlier:
 *
 * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
 * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
 * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
 * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
 * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
 * In game 1, the game could have been played with as few as 4 red, 2 green, and 6 blue cubes. If any color had even one fewer cube, the game would have been impossible.
 * Game 2 could have been played with a minimum of 1 red, 3 green, and 4 blue cubes.
 * Game 3 must have been played with at least 20 red, 13 green, and 6 blue cubes.
 * Game 4 required at least 14 red, 3 green, and 15 blue cubes.
 * Game 5 needed no fewer than 6 red, 3 green, and 2 blue cubes in the bag.
 * The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together. The power of the minimum set of cubes in game 1 is 48. In games 2-5 it was 12, 1560, 630, and 36, respectively. Adding up these five powers produces the sum 2286.
 *
 * For each game, find the minimum set of cubes that must have been present. What is the sum of the power of these sets?
 */

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

