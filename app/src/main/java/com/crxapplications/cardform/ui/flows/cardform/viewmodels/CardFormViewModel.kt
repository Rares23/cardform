package com.crxapplications.cardform.ui.flows.cardform.viewmodels

import android.icu.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.crxapplications.cardform.R
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

enum class CardType {
    VISA,
    UNKNOWN;

    fun cardFrontImageRes(): Int {
        return when (this) {
            VISA -> R.drawable.visa_card_front
            UNKNOWN -> R.drawable.card_placeholder
        }
    }

    fun cardBackImageRes(): Int {
        return when (this) {
            VISA -> R.drawable.visa_card_back
            UNKNOWN -> R.drawable.card_placeholder
        }
    }

    companion object {
        fun from(cardNumber: String): CardType {
            return when {
                cardNumber.startsWith("4") -> VISA
                else -> UNKNOWN
            }
        }
    }
}

data class CardFormState(
    val cardNumber: String = "",
    val cardHolder: String = "",
    val expirationDate: String = "",
    val cvv: String = "",
    val cardType: CardType = CardType.UNKNOWN,
    val showBackSide: Boolean = false,
    val canSubmit: Boolean = false,
    val message: String? = null,
)

interface CardFormActions {
    fun onCardNumberChanged(cardNumber: String)
    fun onCardHolderChanged(cardHolder: String)
    fun onExpirationDateChanged(expirationDate: String)
    fun onCvvChanged(cvv: String)

    fun switchCard(switch: Boolean)

    fun resetMessage()
    fun submit()
}

@HiltViewModel
class CardFormViewModel @Inject constructor() : ViewModel(), CardFormActions {
    var state by mutableStateOf(CardFormState())
        private set

    override fun onCardNumberChanged(cardNumber: String) {
        state = state.copy(
            cardNumber = cardNumber.filter { it.isDigit() },
            cardType = CardType.from(cardNumber),
        )
        checkCanSubmit()
    }

    override fun onCardHolderChanged(cardHolder: String) {
        state = state.copy(cardHolder = cardHolder.filter { it.isLetter() || it.isWhitespace() }.uppercase())
        checkCanSubmit()
    }

    override fun onExpirationDateChanged(expirationDate: String) {
        val now = Calendar.getInstance()
        val currentYear = now.get(Calendar.YEAR) % 100
        val currentMonth = now.get(Calendar.MONTH) + 1

        println("currentYear: $currentYear")

        var date = expirationDate.filter { it.isDigit() }
        val month = if (date.length >= 2) Integer.parseInt(date.take(2)) else 0
        if (month > 12) {
            date = "12"
        }

        val year = if (date.length >= 4) Integer.parseInt(date.takeLast(2)) else 0

        if(date.length >= 4 && (year < currentYear || (year == currentYear && month < currentMonth))) {
            date = "${String.format("%02d", currentMonth)}${String.format("%02d", currentYear)}"
        }


        state = state.copy(expirationDate = date)
        checkCanSubmit()
    }

    override fun onCvvChanged(cvv: String) {
        state = state.copy(cvv = cvv.filter { it.isDigit() },)
        checkCanSubmit()
    }

    override fun switchCard(switch: Boolean) {
        state = state.copy(
            showBackSide = switch
        )
    }

    override fun resetMessage() {
        state = state.copy(
            message = null
        )
    }

    override fun submit() {
        // To be continued
        state = state.copy(
            message = "Submit Pressed!"
        )
    }

    private fun checkCanSubmit() {
        state = state.copy(
            canSubmit = state.cardNumber.length == 16 &&
                    state.cardHolder.isNotBlank() &&
                    state.expirationDate.length == 4 &&
                    state.cvv.length == 3
        )
    }
}