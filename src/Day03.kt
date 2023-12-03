/**
 * --- Day 3: Gear Ratios ---
 * You and the Elf eventually reach a gondola lift station; he says the gondola lift will take you up to the water source, but this is as far as he can bring you. You go inside.
 *
 * It doesn't take long to find the gondolas, but there seems to be a problem: they're not moving.
 *
 * "Aaah!"
 *
 * You turn around to see a slightly-greasy Elf with a wrench and a look of surprise. "Sorry, I wasn't expecting anyone! The gondola lift isn't working right now; it'll still be a while before I can fix it." You offer to help.
 *
 * The engineer explains that an engine part seems to be missing from the engine, but nobody can figure out which one. If you can add up all the part numbers in the engine schematic, it should be easy to work out which part is missing.
 *
 * The engine schematic (your puzzle input) consists of a visual representation of the engine. There are lots of numbers and symbols you don't really understand, but apparently any number adjacent to a symbol, even diagonally, is a "part number" and should be included in your sum. (Periods (.) do not count as a symbol.)
 *
 * Here is an example engine schematic:
 *
 * 467..114..
 * ...*......
 * ..35..633.
 * ......#...
 * 617*......
 * .....+.58.
 * ..592.....
 * ......755.
 * ...$.*....
 * .664.598..
 * In this schematic, two numbers are not part numbers because they are not adjacent to a symbol: 114 (top right) and 58 (middle right). Every other number is adjacent to a symbol and so is a part number; their sum is 4361.
 *
 * Of course, the actual engine schematic is much larger. What is the sum of all of the part numbers in the engine schematic?
 *
 * --- Part Two ---
 * The engineer finds the missing part and installs it in the engine! As the engine springs to life, you jump in the closest gondola, finally ready to ascend to the water source.
 *
 * You don't seem to be going very fast, though. Maybe something is still wrong? Fortunately, the gondola has a phone labeled "help", so you pick it up and the engineer answers.
 *
 * Before you can explain the situation, she suggests that you look out the window. There stands the engineer, holding a phone in one hand and waving with the other. You're going so slowly that you haven't even left the station. You exit the gondola.
 *
 * The missing part wasn't the only issue - one of the gears in the engine is wrong. A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio is the result of multiplying those two numbers together.
 *
 * This time, you need to find the gear ratio of every gear and add them all up so that the engineer can figure out which gear needs to be replaced.
 *
 * Consider the same engine schematic again:
 *
 * 467..114..
 * ...*......
 * ..35..633.
 * ......#...
 * 617*......
 * .....+.58.
 * ..592.....
 * ......755.
 * ...$.*....
 * .664.598..
 * In this schematic, there are two gears. The first is in the top left; it has part numbers 467 and 35, so its gear ratio is 16345. The second gear is in the lower right; its gear ratio is 451490. (The * adjacent to 617 is not a gear because it is only adjacent to one part number.) Adding up all of the gear ratios produces 467835.
 *
 * What is the sum of all of the gear ratios in your engine schematic?
 */

private var currNumber = ""
private var symbolLocations = ArrayList<ElfPoint>()
private var numberLocations = ArrayList<ElfNum>()
private var sumOfResults = 0


private val filteredNumbers = ArrayList<ElfNum>()
private var productOfResults = 0

fun main() {
    fun part1(input: List<String>): Int {
        findLocationOfSymbolsAndNumbers(input)
        findAdjacentSquaresOfSymbols()
        return sumOfResults
    }

    fun part2(input: List<String>): Int {
        findLocationOfGearsAndNumbers(input)
        findAdjacentSquaresOfSymbols()
        println("Here's Filtered Numbers: $filteredNumbers")
        checkAllGears()
        return productOfResults
    }

//    val testInput = readInput("inputFiles/Day03_test")
//    part1(testInput).println()
//    check(symbolLocations.size == 6)
//    check(numberLocations.size == 10)

    val input = readInput("inputFiles/Day03")

    part2(input).println()

}

private fun findLocationOfSymbolsAndNumbers(input: List<String>) {
    input.forEachIndexed { yIndex, rowOfText ->

        var foundNumber = false
        var startPoint: Point? = null

        rowOfText.forEachIndexed { xIndex, currentChar ->
            if (currentChar.isDigit()) {
                val currentPoint = Point(xIndex, yIndex)
                if (!foundNumber) {
                    startPoint = currentPoint
                }
                foundNumber = true
                currNumber += currentChar
                if (xIndex == rowOfText.length-1) {
                    val currElfNum = ElfNum(currNumber.toInt(), startPoint!!, Point(xIndex - 1, yIndex))
                    numberLocations.add(currElfNum)
                    currNumber = ""
                    startPoint = null
                    foundNumber = false
                }
            }
            else {
                if (currentChar != '.') {
                    symbolLocations.add(ElfPoint(Point(xIndex, yIndex)))
                }
                if (foundNumber) {
                    val currElfNum = ElfNum(currNumber.toInt(), startPoint!!, Point(xIndex - 1, yIndex))
                    numberLocations.add(currElfNum)
                    currNumber = ""
                    startPoint = null
                }
                foundNumber = false
            }
        }
    }
}

private fun findLocationOfGearsAndNumbers(input: List<String>) {
    input.forEachIndexed { yIndex, rowOfText ->

        var foundNumber = false
        var startPoint: Point? = null

        rowOfText.forEachIndexed { xIndex, currentChar ->
            if (currentChar.isDigit()) {
                val currentPoint = Point(xIndex, yIndex)
                if (!foundNumber) {
                    println("Start New Number with $currentChar")
                    startPoint = currentPoint
                }
                foundNumber = true
                currNumber += currentChar
                if (xIndex == rowOfText.length-1) {
                    val currElfNum = ElfNum(currNumber.toInt(), startPoint!!, Point(xIndex - 1, yIndex))
                    numberLocations.add(currElfNum)
                    println("Added Number: $currElfNum")
                    currNumber = ""
                    startPoint = null
                    foundNumber = false
                }
            }
            else {
                if (currentChar == '*') {
                    symbolLocations.add(ElfPoint(Point(xIndex, yIndex)))
                }
                if (foundNumber) {
                    val currElfNum = ElfNum(currNumber.toInt(), startPoint!!, Point(xIndex - 1, yIndex))
                    println("Added Number: $currElfNum")
                    numberLocations.add(currElfNum)
                    currNumber = ""
                    startPoint = null
                }
                foundNumber = false
            }
        }
    }
}



private fun findAdjacentSquaresOfSymbols(){
    numberLocations.forEach { currentNumber ->
        checkEachNumberPoint(currentNumber)
    }
}

private fun checkEachNumberPoint(currentNumber: ElfNum){
    currentNumber.listOfPoints.forEach { point ->
        if(checkPointAgainstSymbolLocations(point, currentNumber)) {
            return
        }
    }
}

private fun checkPointAgainstSymbolLocations(point: Point, currentNumber:ElfNum): Boolean{
    symbolLocations.forEach { currentSymbol ->
        if(currentSymbol.checkNumberCollision(point)){
            println("Got collision adding ${currentNumber.numActual}")
            filteredNumbers.add(currentNumber)
            return true
        }
    }
    return false
}


private fun checkAllGears(){
    symbolLocations.forEach{gear ->
        println("Checking gear : $gear")
        checkGearForNumberOfCollisions(gear)
    }
}

private fun checkGearForNumberOfCollisions(currentGear: ElfPoint){
    val gearRatio = ArrayList<Int>()
        filteredNumbers.forEach { currentNumber ->
            currentNumber.listOfPoints.forEach {numberPoint ->
                if(currentGear.checkNumberCollision(numberPoint)){
                    println("Got a collision: ${currentGear} with number : $currentNumber")
                    if(!gearRatio.contains(currentNumber.numActual)) {
                        gearRatio.add(currentNumber.numActual)
                    }
                }
            }
        }
    if (gearRatio.size == 2){
        println("Found gear with ${gearRatio[0]} and ${gearRatio[1]}")
        productOfResults += gearRatio[0] * gearRatio[1]
    }
    gearRatio.clear()
}

data class ElfPoint(val point: Point){
    val leftPoint = Point(point.x - 1, point.y)
    val rightPoint = Point(point.x + 1, point.y)
    val topPoint = Point(point.x, point.y - 1)
    val bottomPoint = Point(point.x, point.y + 1)
    val topLeftPoint = Point(point.x - 1, point.y - 1)
    val bottomLeftPoint = Point(point.x - 1, point.y + 1)
    val topRightPoint = Point(point.x + 1, point.y - 1)
    val bottomRightPoint = Point(point.x + 1, point.y + 1)
    val listOfPoints = ArrayList<Point>()
    init{
        listOfPoints.add(leftPoint)
        listOfPoints.add(rightPoint)
        listOfPoints.add(topPoint)
        listOfPoints.add(bottomPoint)
        listOfPoints.add(topLeftPoint)
        listOfPoints.add(bottomLeftPoint)
        listOfPoints.add(topRightPoint)
        listOfPoints.add(bottomRightPoint)

    }
    fun checkNumberCollision(numberPoint: Point): Boolean{
        return listOfPoints.contains(numberPoint)
    }
}

data class Point(val x: Int, val y: Int)

data class ElfNum(val numActual: Int, val startPoint: Point, val endPoint: Point){
    val listOfPoints = ArrayList<Point>()
    init {
        var range = endPoint.x - startPoint.x
        var tempEndPoint =  endPoint.x

        while(range >= 0){
            val currPoint = Point(tempEndPoint, endPoint.y)
            listOfPoints.add(currPoint)
            tempEndPoint--
            range--
        }
    }

}