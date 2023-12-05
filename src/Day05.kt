/**
 * Enter problem description here
 *
 *
 */
private val listOfSeeds = ArrayList<Float>()
private val mapOfSeedToSoil = ArrayList<String>()
private val mapOfSoilToFertilizer = ArrayList<String>()
private val mapOfFertilizerToWater = ArrayList<String>()
private val mapOfWaterToLight = ArrayList<String>()
private val mapOfLightToTemperature = ArrayList<String>()
private val mapOfTemperatureToHumidity = ArrayList<String>()
private val mapOfHumidityToLocation = ArrayList<String>()
fun main() {
    fun part1(input: List<String>): Float {
        handleInput(input)
        return 1F
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputFiles/Day05_test")
    part1(testInput).println()

    val input = readInput("inputFiles/Day05")
    //part1(input).println()
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
        listOfSeeds.add(seed.toFloat())
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
//
//private fun makeElfSeeds(): Float{
//    var lowestLocationNumber = -1F
//    listOfSeeds.forEach { seed ->
//        println("Seed: $seed")
//
//        val soil = mapOfSeedToSoil[seed] ?: seed
//        println("soil: $soil")
//
//        val fertilizer = mapOfSoilToFertilizer[soil] ?: soil
//        println("fertilizer: $fertilizer")
//
//        val water = mapOfFertilizerToWater[fertilizer] ?: fertilizer
//        println("water: $water")
//
//        val light = mapOfWaterToLight[water] ?: water
//        println("light: $light")
//
//        val temperature = mapOfLightToTemperature[light] ?: light
//        println("temperature: $temperature")
//
//        val humidity = mapOfTemperatureToHumidity[temperature] ?: temperature
//        println("humidity: $humidity")
//
//        val location = mapOfHumidityToLocation[humidity] ?: humidity
//        println("location: $location")
//
//        if(location < lowestLocationNumber || lowestLocationNumber == -1F){
//            lowestLocationNumber = location
//        }
//    }
//    return lowestLocationNumber
//}

data class ElfSeed(val seedNum: Float, val soil : Float, val fertilizer : Float, val water : Float, val light: Float, val temperature: Float, val humidity: Float, val location: Float)