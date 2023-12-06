import java.math.BigInteger

/**
 * Enter problem description here
 *
 *
 */
private var listOfTimes = ArrayList<String>()
private var listOfMaxDistances = ArrayList<String>()

private var singleTime = ""
private var singleDistance = ""

fun main() {
    fun part1(input: List<String>): BigInteger {
        handleInput(input)
        println(listOfTimes)
        println(listOfMaxDistances)
        val result = processTimes()
        result.println()
        return result
    }

    fun part2(input: List<String>): Int {
        handleInput2(input)
        singleTime.println()
        singleDistance.println()
        val result = processTimes2()
        result.println()
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputFiles/Day06_test")
    //check(part1(testInput) == BigInteger.valueOf(288))

    val input = readInput("inputFiles/Day06")
    //part1(input)
    part2(input).println()
}

private fun processTimes(): BigInteger{
    var index = 0
    var totalNumBeaten = BigInteger.ONE
    while(index < listOfTimes.size){
        val currTime = listOfTimes[index]
        val currMaxDist = listOfMaxDistances[index]
        val currBoat = ToyBoat(currTime.toBigInteger(), currMaxDist.toBigInteger())
        totalNumBeaten *= currBoat.countBeaten
        index++
    }
    return totalNumBeaten
}

private fun processTimes2(): BigInteger{

    val currBoat = ToyBoat(singleTime.toBigInteger(), singleDistance.toBigInteger())
    return currBoat.countBeaten

}



private fun handleInput(input: List<String>){
    input.forEach { singleLineInput ->
        val splitInput = singleLineInput.split(":")
        if(splitInput[0]=="Time"){
            val times = splitInput[1].trim()
            val timeSplit = times.split(" ")
            timeSplit.forEach { time ->
                if(time.isNotEmpty()){
                    listOfTimes.add(time)
                }
            }
        }
        else if(splitInput[0]=="Distance"){
            val distances = splitInput[1].trim()
            val distanceSplit = distances.split(" ")
            distanceSplit.forEach { distance ->
                if(distance.isNotEmpty()){
                    listOfMaxDistances.add(distance)
                }
            }
        }
    }
}
private fun handleInput2(input: List<String>){
    input.forEach { singleLineInput ->
        val splitInput = singleLineInput.split(":")
        if(splitInput[0]=="Time"){
            val times = splitInput[1].trim()
            val timeSplit = times.split(" ")
            timeSplit.forEach { time ->
                if(time.isNotEmpty()){
                    singleTime += time
                }
            }
        }
        else if(splitInput[0]=="Distance"){
            val distances = splitInput[1].trim()
            val distanceSplit = distances.split(" ")
            distanceSplit.forEach { distance ->
                if(distance.isNotEmpty()){
                    singleDistance += distance
                }
            }
        }
    }
}

data class ToyBoat(var totalTime: BigInteger, val boatRecord: BigInteger){
    var maxDistance = BigInteger.ZERO
    val minSpeed = BigInteger.ZERO
    val maxSpeed = totalTime

    var currentSpeed = minSpeed

    var timeTravelled = BigInteger.ZERO

    var distanceTravelled = BigInteger.ZERO
    var countBeaten = BigInteger.ZERO
    init{
        findMaxDistance()
    }
    private fun findMaxDistance() {
        while (currentSpeed < maxSpeed) {
            currentSpeed++
            println("currentSpeed: $currentSpeed")
            timeTravelled = totalTime - currentSpeed
            println("timeTravelled: $timeTravelled")
            distanceTravelled = timeTravelled * currentSpeed
            println("distanceTravelled: $distanceTravelled")

            if (distanceTravelled > maxDistance) {
                maxDistance = distanceTravelled
            }
            if(distanceTravelled > boatRecord){
                countBeaten++
            }
        }
    }
}