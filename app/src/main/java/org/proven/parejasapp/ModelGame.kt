package org.proven.parejasapp

data class Card(
    val id: Int,
    val imageResId: Int,
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false
)

class ModelGame {

    private val cardImages = listOf(
        R.drawable.chiquito,
        R.drawable.eugenio,
        R.drawable.faemino
    )

    var cards: List<Card> = emptyList()
        private set

    val selectedIndices = mutableListOf<Int>()

    var isGameOver: Boolean = false
        private set

    init {
        initGame()
    }

    fun initGame() {
        val list = mutableListOf<Card>()
        var idCounter = 0
        for (imgRes in cardImages) {
            list.add(Card(id = idCounter++, imageResId = imgRes))
            list.add(Card(id = idCounter++, imageResId = imgRes))
        }
        list.shuffle()
        cards = list
        selectedIndices.clear()
        isGameOver = false
    }

    fun selectCard(index: Int): Boolean {
        if (index !in cards.indices) return false
        val card = cards[index]

        if (card.isMatched || card.isFaceUp || selectedIndices.size >= 2) {
            return false
        }

        val updatedCards = cards.toMutableList()
        updatedCards[index] = card.copy(isFaceUp = true)
        cards = updatedCards
        selectedIndices.add(index)
        return true
    }

    fun validateTurn(): Boolean {
        if (selectedIndices.size != 2) return false

        val idx1 = selectedIndices[0]
        val idx2 = selectedIndices[1]
        val card1 = cards[idx1]
        val card2 = cards[idx2]

        val updatedCards = cards.toMutableList()

        if (card1.imageResId == card2.imageResId) {
            // Pareja correcta: se quedan destapadas y se marcan como pareadas
            updatedCards[idx1] = card1.copy(isMatched = true, isFaceUp = true)
            updatedCards[idx2] = card2.copy(isMatched = true, isFaceUp = true)
        } else {
            // Pareja incorrecta: se vuelven a tapar
            updatedCards[idx1] = card1.copy(isFaceUp = false)
            updatedCards[idx2] = card2.copy(isFaceUp = false)
        }

        cards = updatedCards
        selectedIndices.clear()

        if (cards.all { it.isMatched }) {
            isGameOver = true
        }

        return true
    }
}
