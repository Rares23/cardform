package com.crxapplications.cardform.ui.flows.cardform.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class CardType {
    VISA,
    UNKNOWN
}

data class CardFormState(
    val cardNumber: String = "",
    val cardHolder: String = "",
    val expirationDate: String = "",
    val cvv: String = "",
    val cardType: CardType = CardType.UNKNOWN,
)

interface CardFormActions {
    fun onCardNumberChanged(cardNumber: String)
    fun onCardHolderChanged(cardHolder: String)
    fun onExpirationDateChanged(expirationDate: String)
    fun onCvvChanged(cvv: String)
}

@HiltViewModel
class CardFormViewModel @Inject constructor() : ViewModel(), CardFormActions {
    var state by mutableStateOf(CardFormState())
        private set

    override fun onCardNumberChanged(cardNumber: String) {
        TODO("Not yet implemented")
    }

    override fun onCardHolderChanged(cardHolder: String) {
        TODO("Not yet implemented")
    }

    override fun onExpirationDateChanged(expirationDate: String) {
        TODO("Not yet implemented")
    }

    override fun onCvvChanged(cvv: String) {
        TODO("Not yet implemented")
    }
}