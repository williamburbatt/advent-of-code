import java.math.BigInteger

/**
 * Enter problem description here
 *
 *
 */
private val arrayOfHands = ArrayList<CardHand>()
fun main() {
    fun part1(input: List<String>): BigInteger {
        var output: BigInteger = BigInteger.ZERO
        input.forEach{singleLine ->
            val inputSplit = singleLine.split(" ")
            val cardValues = inputSplit[0]
            val points = inputSplit[1]
            val currentHand = CardHand(cardValues,points.toInt())
            arrayOfHands.add(currentHand)
        }
        var index = 0
        arrayOfHands.sortDescending()
        while(index < arrayOfHands.size){

            val currMultiplier = (arrayOfHands.size - index)
            val currBet = arrayOfHands[index].pointsBet
            val currHand = arrayOfHands[index].cards
            val currType = arrayOfHands[index].handRank
            val currWinnings = currBet * currMultiplier

            print("currHand: $currHand ")
            print("currType: $currType ")
            print("currBet: $currBet ")
            print("currMultiplier: $currMultiplier ")
            println("currWinnings: $currWinnings")
//                print(currHand)
//                println(" $currBet")
            output += currWinnings.toBigInteger()
            index++
        }
        output.println()
        return output
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputFiles/Day07_test")
    //check(part1(testInput) == BigInteger.valueOf(5905))

    val input = readInput("inputFiles/Day07")
    part1(input).println()
    //part2(input).println()
}
data class CardHand(var cards: String, val pointsBet: Int) :Comparable<CardHand>{
    val arrayOfCards = ArrayList<Card>()
    var handRank = TypeOfHand.HIGH_CARD
    var handRankValue = 1
    var hasPair = -1
    var hasThreeOfAKind = false
    var mostCardOccValue = ' '
    var highestValueCard = Card('J')

    init {
        fillArrayOfCards()
        findRankJokers()
    }

    private fun fillArrayOfCards() {
        arrayOfCards.clear()
        cards.forEach { cardFaceValue ->
            val tempCard = Card(cardFaceValue)
            if(tempCard.value > highestValueCard.value){
                highestValueCard = tempCard
            }
            arrayOfCards.add(Card(cardFaceValue))
        }
    }

    private fun findRankJokers() {
        findRank()
        if(cards.contains("J")){
            val oldCards = cards
            println("cards $oldCards contains jokers")
            val otherCards = cards.filter { it -> it != 'J' }
            val jokers = cards.filter { it -> it == 'J' }
            println("handsRank: $handRank")
            println("otherCards: $otherCards")
            println("jokers: $jokers")
            when (jokers.length) {
                4, 3 -> {
                    cards = cards.replace("J", highestValueCard.toString())

                }
                2 -> {
                    if(handRank == TypeOfHand.THREE_OF_A_KIND || handRank == TypeOfHand.FULL_HOUSE || handRank == TypeOfHand.TWO_PAIR){
                        cards = cards.replace("J", mostCardOccValue.toString())
                    }
                    else{
                        cards = cards.replace("J", highestValueCard.faceValue.toString())
                    }
                }
                else -> {
                    when (handRank) {
                        TypeOfHand.THREE_OF_A_KIND -> {
                            cards = cards.replace("J", mostCardOccValue.toString())
                        }
                        TypeOfHand.TWO_PAIR -> {
                            cards = cards.replace("J", highestValueCard.faceValue.toString())
                        }
                        TypeOfHand.ONE_PAIR -> {
                            cards = cards.replace("J", mostCardOccValue.toString())
                        }
                        else -> {
                            cards = cards.replace("J", highestValueCard.faceValue.toString())
                        }
                    }
                }
            }
            println("new cards $cards")
            fillArrayOfCards()
            findRank()
            cards = oldCards
            fillArrayOfCards()
            println("Original Cards: $cards")

        }
    }

    private fun findRank(){
        hasPair = -1
        hasThreeOfAKind = false

        println("checking array of cards $arrayOfCards")
        arrayOfCards.forEach { card ->
            println("checking card $card")
            val allOccurances = arrayOfCards.filter { it.value == card.value }
            rankHelper(allOccurances.size, allOccurances[0].value, card.faceValue)
        }
    }

    private fun rankHelper(numberOfOccurrences: Int, currentCardValue: Int, currentCardFace: Char) {
        var newHandRank = 1
        when (numberOfOccurrences) {
            2 -> {
                newHandRank = checkPair(currentCardValue)
            }
            3 -> {
                newHandRank = checkThreeOfAKind(currentCardFace)
            }

            4 -> {
                newHandRank = 6
                handRank = TypeOfHand.FOUR_OF_A_KIND
            }
            5 -> {
                newHandRank = 7
                handRank = TypeOfHand.FIVE_OF_A_KIND
            }
        }
        if (newHandRank >= handRankValue) {
            handRankValue = newHandRank
            if(currentCardFace != 'J') {
                mostCardOccValue = currentCardFace
            }
        }
    }

    private fun checkPair(currentCardValue: Int): Int{
        var newHandRank = -1
        if (hasPair == -1) {
            hasPair = currentCardValue
            handRank = TypeOfHand.ONE_PAIR
            newHandRank = 2
        } else {
            if(currentCardValue != hasPair) {
                handRank = TypeOfHand.TWO_PAIR
                newHandRank = 3
            }
        }
        if(checkFullHouse()){
            newHandRank = 5
            handRank = TypeOfHand.FULL_HOUSE

        }
        return newHandRank
    }
    private fun checkThreeOfAKind(currentCardFace: Char): Int{
        var newHandRank = -1
        if(currentCardFace!= 'J') {
            mostCardOccValue = currentCardFace
        }
        hasThreeOfAKind = true
        handRank = TypeOfHand.THREE_OF_A_KIND
        if(checkFullHouse()){
            newHandRank = 5
            handRank = TypeOfHand.FULL_HOUSE
        }
        else{
            newHandRank = 4
        }
        return newHandRank
    }

    private fun checkFullHouse():Boolean{
        return hasPair != -1 && hasThreeOfAKind
    }

    override fun compareTo(other: CardHand): Int {
        val checkHandsValue = this.handRankValue.compareTo(other.handRankValue)
        if(checkHandsValue != 0){
            return checkHandsValue
        }

        for (i in 0 until arrayOfCards.size) {
            val checkSingleCardValues = this.arrayOfCards[i].compareTo(other.arrayOfCards[i])
            if (checkSingleCardValues != 0) {
                return checkSingleCardValues
            }
        }
        return 0
    }

    override fun toString(): String {
        return "Hand: $cards Points Bet: $pointsBet Rank: $handRank Value: $handRankValue"
    }
}
enum class TypeOfHand{
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
}

data class Card(val faceValue: Char):Comparable<Card>{
    val value = findValue()
    private fun findValue():Int{

        if(faceValue.isDigit()){
           return faceValue.digitToInt()
        }
        when(faceValue){
            'T'->{
                return 10
            }
            'J' ->{
                return 1
            }
            'Q' -> {
                return 12
            }
            'K' -> {
                return 13
            }
            'A' -> {
                return 14
            }
        }
        return -1
    }

    override fun compareTo(other: Card): Int {
       return this.value.compareTo(other.value)
    }
}