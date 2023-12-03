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