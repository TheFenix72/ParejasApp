package org.proven.parejasapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val modelGame = ModelGame()

    var cards by mutableStateOf(modelGame.cards)
        private set

    var selectedCount by mutableStateOf(0)
        private set

    var isGameOver by mutableStateOf(false)
        private set

    fun onCardClick(index: Int) {
        if (modelGame.selectCard(index)) {
            syncState()
        }
    }

    fun onValidate() {
        if (modelGame.validateTurn()) {
            syncState()
        }
    }

    fun onReset() {
        modelGame.initGame()
        syncState()
    }

    private fun syncState() {
        cards = modelGame.cards.toList()
        selectedCount = modelGame.selectedIndices.size
        isGameOver = modelGame.isGameOver
    }
}
