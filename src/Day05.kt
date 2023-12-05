import java.math.BigDecimal

/**
 *--- Day 5: If You Give A Seed A Fertilizer ---
 * You take the boat and find the gardener right where you were told he would be: managing a giant "garden" that looks more to you like a farm.
 *
 * "A water source? Island Island is the water source!" You point out that Snow Island isn't receiving any water.
 *
 * "Oh, we had to stop the water because we ran out of sand to filter it with! Can't make snow with dirty water. Don't worry, I'm sure we'll get more sand soon; we only turned off the water a few days... weeks... oh no." His face sinks into a look of horrified realization.
 *
 * "I've been so busy making sure everyone here has food that I completely forgot to check why we stopped getting more sand! There's a ferry leaving soon that is headed over in that direction - it's much faster than your boat. Could you please go check it out?"
 *
 * You barely have time to agree to this request when he brings up another. "While you wait for the ferry, maybe you can help us with our food production problem. The latest Island Island Almanac just arrived and we're having trouble making sense of it."
 *
 * The almanac (your puzzle input) lists all of the seeds that need to be planted. It also lists what type of soil to use with each kind of seed, what type of fertilizer to use with each kind of soil, what type of water to use with each kind of fertilizer, and so on. Every type of seed, soil, fertilizer and so on is identified with a number, but numbers are reused by each category - that is, soil 123 and fertilizer 123 aren't necessarily related to each other.
 *
 * For example:
 *
 * seeds: 79 14 55 13
 *
 * seed-to-soil map:
 * 50 98 2
 * 52 50 48
 *
 * soil-to-fertilizer map:
 * 0 15 37
 * 37 52 2
 * 39 0 15
 *
 * fertilizer-to-water map:
 * 49 53 8
 * 0 11 42
 * 42 0 7
 * 57 7 4
 *
 * water-to-light map:
 * 88 18 7
 * 18 25 70
 *
 * light-to-temperature map:
 * 45 77 23
 * 81 45 19
 * 68 64 13
 *
 * temperature-to-humidity map:
 * 0 69 1
 * 1 0 69
 *
 * humidity-to-location map:
 * 60 56 37
 * 56 93 4
 * The almanac starts by listing which seeds need to be planted: seeds 79, 14, 55, and 13.
 *
 * The rest of the almanac contains a list of maps which describe how to convert numbers from a source category into numbers in a destination category. That is, the section that starts with seed-to-soil map: describes how to convert a seed number (the source) to a soil number (the destination). This lets the gardener and his team know which soil to use with which seeds, which water to use with which fertilizer, and so on.
 *
 * Rather than list every source number and its corresponding destination number one by one, the maps describe entire ranges of numbers that can be converted. Each line within a map contains three numbers: the destination range start, the source range start, and the range length.
 *
 * Consider again the example seed-to-soil map:
 *
 * 50 98 2
 * 52 50 48
 * The first line has a destination range start of 50, a source range start of 98, and a range length of 2. This line means that the source range starts at 98 and contains two values: 98 and 99. The destination range is the same length, but it starts at 50, so its two values are 50 and 51. With this information, you know that seed number 98 corresponds to soil number 50 and that seed number 99 corresponds to soil number 51.
 *
 * The second line means that the source range starts at 50 and contains 48 values: 50, 51, ..., 96, 97. This corresponds to a destination range starting at 52 and also containing 48 values: 52, 53, ..., 98, 99. So, seed number 53 corresponds to soil number 55.
 *
 * Any source numbers that aren't mapped correspond to the same destination number. So, seed number 10 corresponds to soil number 10.
 *
 * So, the entire list of seed numbers and their corresponding soil numbers looks like this:
 *
 * seed  soil
 * 0     0
 * 1     1
 * ...   ...
 * 48    48
 * 49    49
 * 50    52
 * 51    53
 * ...   ...
 * 96    98
 * 97    99
 * 98    50
 * 99    51
 * With this map, you can look up the soil number required for each initial seed number:
 *
 * Seed number 79 corresponds to soil number 81.
 * Seed number 14 corresponds to soil number 14.
 * Seed number 55 corresponds to soil number 57.
 * Seed number 13 corresponds to soil number 13.
 * The gardener and his team want to get started as soon as possible, so they'd like to know the closest location that needs a seed. Using these maps, find the lowest location number that corresponds to any of the initial seeds. To do this, you'll need to convert each seed number through other categories until you can find its corresponding location number. In this example, the corresponding types are:
 *
 * Seed 79, soil 81, fertilizer 81, water 81, light 74, temperature 78, humidity 78, location 82.
 * Seed 14, soil 14, fertilizer 53, water 49, light 42, temperature 42, humidity 43, location 43.
 * Seed 55, soil 57, fertilizer 57, water 53, light 46, temperature 82, humidity 82, location 86.
 * Seed 13, soil 13, fertilizer 52, water 41, light 34, temperature 34, humidity 35, location 35.
 * So, the lowest location number in this example is 35.
 *
 * What is the lowest location number that corresponds to any of the initial seed numbers?
 *
 *
 */
private val listOfSeeds = ArrayList<ElfSeed>()
private val mapOfSeedToSoil = ArrayList<String>()
private val mapOfSoilToFertilizer = ArrayList<String>()
private val mapOfFertilizerToWater = ArrayList<String>()
private val mapOfWaterToLight = ArrayList<String>()
private val mapOfLightToTemperature = ArrayList<String>()
private val mapOfTemperatureToHumidity = ArrayList<String>()
private val mapOfHumidityToLocation = ArrayList<String>()
fun main() {
    fun part1(input: List<String>): String {
        handleInput(input)
        checkSeedsAgainstSoil()
        println("Print seeds:")
        listOfSeeds.forEach {
            it.println()
        }
        return findSmallestLocation()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("inputFiles/Day05_test")
//    part1(testInput).println()

    val input = readInput("inputFiles/Day05")
    val result = part1(input)
        println(result)
//    part2(input).println()
}

private fun handleInput(input:List<String>){
    val currentInput = ArrayList<String>()
    input.forEach {singleInputLine ->
        when{
            (singleInputLine.contains("seeds:")) ->{
                handleSeeds(singleInputLine)
            }
            singleInputLine.isEmpty() ->{
                if(currentInput.isNotEmpty()) {
                    handleMap(currentInput)
                    currentInput.clear()
                }
            }
            else ->{
                currentInput.add(singleInputLine)
            }
        }
    }
    if(currentInput.isNotEmpty()) {
    handleMap(currentInput)
    }
}
private fun handleSeeds(seeds: String){
    val seedSplit = seeds.split(":")
    val seedsActual = seedSplit[1].trim()
    val seedList = seedsActual.split(" ")
    seedList.forEach { seed ->
        listOfSeeds.add(ElfSeed(seedNum = seed.toBigDecimal()))
    }
}

private fun handleMap(filterInput: ArrayList<String>){
    val type = filterInput.removeAt(0).replace("map:","").trim()
    type.println()
    filterInput.forEach { singleLineOfInput ->
        storeMap(singleLineOfInput, type)
    } }

private fun storeMap(input: String, mapType: String){
    when(mapType){
        "seed-to-soil" ->{
            mapOfSeedToSoil.add(input)
        }
        "soil-to-fertilizer" ->{
            mapOfSoilToFertilizer.add(input)
        }
        "fertilizer-to-water" ->{
            mapOfFertilizerToWater.add(input)
        }
        "water-to-light" ->{
            mapOfWaterToLight.add(input)
        }
        "light-to-temperature" ->{
            mapOfLightToTemperature.add(input)
        }
        "temperature-to-humidity" ->{
            mapOfTemperatureToHumidity.add(input)
        }
        "humidity-to-location" ->{
            mapOfHumidityToLocation.add(input)
        }
    }
}
private fun checkSeedsAgainstSoil(){
    listOfSeeds.forEach{seed->
        println("~~~~~~~~~~~~ Checking seed: $seed ~~~~~~~~~~~~~~~~~~~~~~~~~")
        checkEachSoilMapAgainstSeed(seed, seed.seedNum, mapOfSeedToSoil, "soil")
        checkEachSoilMapAgainstSeed(seed, seed.soil!!, mapOfSoilToFertilizer, "fertilizer")
        checkEachSoilMapAgainstSeed(seed, seed.fertilizer!!, mapOfFertilizerToWater, "water")
        checkEachSoilMapAgainstSeed(seed, seed.water!!, mapOfWaterToLight, "light")
        checkEachSoilMapAgainstSeed(seed, seed.light!!, mapOfLightToTemperature, "temperature")
        checkEachSoilMapAgainstSeed(seed, seed.temperature!!, mapOfTemperatureToHumidity, "humidity")
        checkEachSoilMapAgainstSeed(seed, seed.humidity!!, mapOfHumidityToLocation, "location")
        println("~~~~~~~~~~~~ End seed: $seed ~~~~~~~~~~~~~~~~~~~~~~~~~")
    }
}
private fun checkEachSoilMapAgainstSeed(seed: ElfSeed, knownAttribute: BigDecimal, mapToCheck: ArrayList<String>, newAttributeName: String){
    println("Check $newAttributeName maps for $knownAttribute")
    mapToCheck.forEach{mapActual ->
        println("--------------------------")
        println("Checking new map: $mapActual")
        val splitMap = mapActual.split(" ")
        val source = splitMap[1]
        val sourceNum = source.toBigDecimal()
        println("Source Start: $source")

        val destination = splitMap[0]
        val destinationNum = destination.toBigDecimal()

        println("Destination Start: $destination")

        val range = splitMap[2]
        val rangeNum = range.toBigDecimal()
        println("Range: $range")

        val sourceEndNum = sourceNum + rangeNum


        if(knownAttribute in sourceNum..sourceEndNum){
            println("$knownAttribute is between start: $source and end: $sourceEndNum")
            val positionOfSeed = knownAttribute - sourceNum
            val positionOfDestination = destinationNum + positionOfSeed
            setNewAttribute(seed, positionOfDestination, newAttributeName)
            return
        }
        else{
            println("$knownAttribute is NOT between start: $source and end: ${source+range}")
        }

    }
    println("$knownAttribute is does NOT fit on these maps")
    setNewAttribute(seed, knownAttribute, newAttributeName)
}
private fun setNewAttribute(seed: ElfSeed, newAttribute: BigDecimal, newAttributeName: String) {
    println("--------------------------")
    println()
    println("Set $newAttributeName to $newAttribute")
    println()
    println("--------------------------")
    when (newAttributeName) {
        "soil"-> seed.soil = newAttribute
        "fertilizer"-> seed.fertilizer = newAttribute
        "water"-> seed.water = newAttribute
        "light"-> seed.light = newAttribute
        "temperature"-> seed.temperature = newAttribute
        "humidity"-> seed.humidity = newAttribute
        "location"-> seed.location = newAttribute
    }
}

private fun findSmallestLocation(): String{
    var smallest = "-1".toBigDecimal()
    listOfSeeds.forEach {seed ->
        val currentLocation = seed.location!!
        println("Checking location: ${currentLocation}")
        if(smallest == BigDecimal(-1) || currentLocation < smallest){
            println("Got new lowest: ${currentLocation}")
            smallest = currentLocation
        }
    }
    return smallest.toString()
}

data class ElfSeed(val seedNum: BigDecimal, var soil : BigDecimal? = null, var fertilizer : BigDecimal? = null, var water : BigDecimal? = null, var light: BigDecimal? = null, var temperature: BigDecimal? = null, var humidity: BigDecimal? = null, var location: BigDecimal? = null)