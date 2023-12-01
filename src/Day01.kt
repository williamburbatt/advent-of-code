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
        val arrayListOfDigits = arrayListOf("one", "two", "three","four","five","six","seven","eight","nine")
        var sumOfAllDigits = 0

        input.forEach { singleInputString ->
            var firstIndex = singleInputString.length
            var firstNumber = 0

            var lastIndex = -1
            var lastNumber = 0

            arrayListOfDigits.forEach{ num ->
                //println("num:$num")

                val digitActual = arrayListOfDigits.indexOf(num)+1
                val firstIndexOfStringActual = singleInputString.indexOf(num)
                val firstIndexOfDigitActual = singleInputString.indexOf(digitActual.toString())

                val lastIndexOfStringActual = singleInputString.lastIndexOf(num)
                val lastIndexOfDigitActual = singleInputString.lastIndexOf(digitActual.toString())

                if(firstIndexOfStringActual > -1){
                //println("Found num string:$num")

                    if(firstIndexOfStringActual < firstIndex){
                            firstIndex = firstIndexOfStringActual
                            firstNumber = digitActual
                        }
                        if(lastIndexOfStringActual > lastIndex){
                            lastIndex = lastIndexOfStringActual
                            lastNumber =  digitActual
                        }
                    }

                if(firstIndexOfDigitActual > -1){
                    //println("Found num digit:$num")

                    if(firstIndexOfDigitActual < firstIndex){
                            firstIndex = firstIndexOfDigitActual
                            firstNumber = digitActual
                        }
                        if(lastIndexOfDigitActual > lastIndex){
                            lastIndex = lastIndexOfDigitActual
                            lastNumber =  digitActual
                        }
                    }

            //println()
            }
            val result = (firstNumber.toString() + lastNumber.toString())
            println(result)
            sumOfAllDigits += result.toInt()

        }
        return sumOfAllDigits
    }

    val testInput = readInput("inputFiles/Day01Part2")
    val input = readInput("inputFiles/Day01")
//    println("This is Test Data:")
//    part2(testInput).println()
    println("This is the actual input: ")
    part2(input).println()
}
