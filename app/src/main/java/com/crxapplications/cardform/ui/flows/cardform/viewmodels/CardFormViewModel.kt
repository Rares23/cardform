package com.crxapplications.cardform.ui.flows.cardform.viewmodels

import android.icu.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.crxapplications.cardform.R
import com.crxapplications.cardform.ui.core.utils.UiText
import com.crxapplications.cardform.ui.flows.cardform.validators.CardValidator
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
    val cardNumber: CardInputData = CardInputData(),
    val cardHolder: CardInputData = CardInputData(),
    val expirationDate: CardInputData = CardInputData(),
    val cvv: CardInputData = CardInputData(),
    val cardType: CardType = CardType.UNKNOWN,
    val showBackSide: Boolean = false,
    val validations: HashMap<String, String> = hashMapOf(),
    val message: UiText? = null,
    val submittedSuccessfully: Boolean? = null,
)

data class CardInputData(
    val value: String = "",
    val error: UiText? = null,
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
class CardFormViewModel @Inject constructor(
    private val cardValidator: CardValidator,
) : ViewModel(), CardFormActions {
    var state by mutableStateOf(CardFormState())
        private set

    override fun onCardNumberChanged(cardNumber: String) {
        val tmpCardNumber = cardNumber.filter { it.isDigit() }

        state = state.copy(
            cardNumber = state.cardNumber.copy(
                value = tmpCardNumber,
                error = cardValidator.validateCardNumber(tmpCardNumber)
            ),
            cardType = CardType.from(tmpCardNumber),
        )

        isValid()
    }

    override fun onCardHolderChanged(cardHolder: String) {
        val tmpCardHolder = cardHolder.filter { it.isLetter() || it.isWhitespace() }.uppercase()
        state = state.copy(
            cardHolder = state.cardHolder.copy(
                value = tmpCardHolder,
                error = cardValidator.validateCardHolder(tmpCardHolder)
            )
        )

        isValid()
    }

    override fun onExpirationDateChanged(expirationDate: String) {
        val date = expirationDate.filter { it.isDigit() }

        state = state.copy(
            expirationDate = state.expirationDate.copy(
                value = date,
                error = cardValidator.validateExpirationDate(date)
            )
        )
        isValid()
    }

    override fun onCvvChanged(cvv: String) {
        val tmpCvv = cvv.filter { it.isDigit() }
        state = state.copy(
            cvv = state.cvv.copy(
                value = tmpCvv,
                error = cardValidator.validateCvv(tmpCvv)
            )
        )
        isValid()
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
        state = state.copy(
            cardNumber = state.cardNumber.copy(
                error = cardValidator.validateCardNumber(state.cardNumber.value)
            ),
            cardHolder = state.cardHolder.copy(
                error = cardValidator.validateCardHolder(state.cardHolder.value)
            ),
            expirationDate = state.expirationDate.copy(
                error = cardValidator.validateExpirationDate(state.expirationDate.value)
            ),
            cvv = state.cvv.copy(
                error = cardValidator.validateCvv(state.cvv.value)
            ),
        )

        val isValid = isValid()

        state = state.copy(
            message = if (isValid) UiText.ResourceString(R.string.submit_success_message) else UiText.ResourceString(
                R.string.card_validation_message
            ),
            submittedSuccessfully = isValid,
        )
    }

    private fun isValid(): Boolean {
        return state.cardNumber.error == null &&
                state.cardHolder.error == null &&
                state.expirationDate.error == null &&
                state.cvv.error == null
    }
}