package com.crxapplications.cardform.ui.flows.cardform.utils

import com.crxapplications.cardform.ui.core.utils.UiText
import com.crxapplications.cardform.ui.flows.cardform.validators.CardValidator

class CardValidatorMockImpl(
    private val isCardNumberValid: Boolean = false,
    private val isCardHolderValid: Boolean = false,
    private val isExpirationDateValid: Boolean = false,
    private val isCvvValid: Boolean = false,
) : CardValidator {

    override fun validateCardNumber(cardNumber: String): UiText? =
        if (isCardNumberValid) null else UiText.DynamicString("Invalid card number")

    override fun validateCardHolder(cardHolder: String): UiText? =
        if (isCardHolderValid) null else UiText.DynamicString("Invalid card holder")

    override fun validateExpirationDate(expirationDate: String): UiText? =
        if (isExpirationDateValid) null else UiText.DynamicString("Invalid expiration date")

    override fun validateCvv(cvv: String): UiText? =
        if (isCvvValid) null else UiText.DynamicString("Invalid cvv")
}