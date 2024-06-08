package com.crxapplications.cardform.ui.flows.cardform.validators

import android.icu.util.Calendar
import com.crxapplications.cardform.R
import com.crxapplications.cardform.ui.core.utils.UiText

interface CardValidator {
    fun validateCardNumber(cardNumber: String): UiText?
    fun validateCardHolder(cardHolder: String): UiText?
    fun validateExpirationDate(expirationDate: String): UiText?
    fun validateCvv(cvv: String): UiText?
}

class CardValidatorImpl: CardValidator {
    override fun validateCardNumber(cardNumber: String): UiText? {
        if (cardNumber.length < 16) {
            return UiText.ResourceString(R.string.input_invalid_message)
        }

        return null
    }

    override fun validateCardHolder(cardHolder: String): UiText? {
        if (cardHolder.length < 3) {
            return UiText.ResourceString(R.string.input_invalid_message)
        }
        return null
    }

    override fun validateExpirationDate(expirationDate: String): UiText? {
        val now = Calendar.getInstance()
        val currentYear = now.get(Calendar.YEAR) % 100
        val currentMonth = now.get(Calendar.MONTH) + 1


        val month = if (expirationDate.length >= 2) Integer.parseInt(expirationDate.take(2)) else 0
        if (month > 12) {
            return UiText.ResourceString(R.string.input_invalid_message)
        }

        val year =
            if (expirationDate.length >= 4) Integer.parseInt(expirationDate.takeLast(2)) else 0

        if (expirationDate.length < 4) {
            return UiText.ResourceString(R.string.input_invalid_message)
        }

        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            return UiText.ResourceString(R.string.card_expiration_date_is_expired_message)
        }

        return null
    }

    override fun validateCvv(cvv: String): UiText? {
        if (cvv.length < 3) {
            return UiText.ResourceString(R.string.input_invalid_message)
        }

        return null
    }
}