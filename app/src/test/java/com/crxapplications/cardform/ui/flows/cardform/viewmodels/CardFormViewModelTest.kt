package com.crxapplications.cardform.ui.flows.cardform.viewmodels

import com.crxapplications.cardform.ui.flows.cardform.utils.CardValidatorMockImpl
import com.crxapplications.cardform.ui.flows.cardform.validators.CardValidator
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CardFormViewModelTest {
    private lateinit var mockedCardValidator: CardValidator

    private lateinit var cardFormViewModel: CardFormViewModel

    @Test
    fun `when card data is valid submit success`() {
        val cardNumber = "1234 5678 9012 3456"
        val cardHolder = "John Snow"
        val expirationDate = "12/24"
        val cvv = "123"

        mockedCardValidator = CardValidatorMockImpl(
            isCardNumberValid = true,
            isCardHolderValid = true,
            isExpirationDateValid = true,
            isCvvValid = true
        )

        cardFormViewModel = CardFormViewModel(
            mockedCardValidator
        )

        cardFormViewModel.onCardNumberChanged(cardNumber)
        cardFormViewModel.onExpirationDateChanged(expirationDate)
        cardFormViewModel.onCvvChanged(cvv)
        cardFormViewModel.onCardHolderChanged(cardHolder)

        cardFormViewModel.submit()

        val state = cardFormViewModel.state

        assertTrue(state.submittedSuccessfully == true)
    }

    @Test
    fun `when card number is invalid submit failed`() {
        val cardNumber = "123 5678 9012 3456"
        val cardHolder = "John Snow"
        val expirationDate = "12/24"
        val cvv = "123"

        mockedCardValidator = CardValidatorMockImpl(
            isCardNumberValid = false,
            isCardHolderValid = true,
            isExpirationDateValid = true,
            isCvvValid = true
        )

        cardFormViewModel = CardFormViewModel(
            mockedCardValidator
        )

        cardFormViewModel.onCardNumberChanged(cardNumber)
        cardFormViewModel.onExpirationDateChanged(expirationDate)
        cardFormViewModel.onCvvChanged(cvv)
        cardFormViewModel.onCardHolderChanged(cardHolder)

        cardFormViewModel.submit()

        val state = cardFormViewModel.state

        assertTrue(state.submittedSuccessfully == false)
    }

    @Test
    fun `when card holder is invalid submit failed`() {
        val cardNumber = "123 5678 9012 3456"
        val cardHolder = "John Snow"
        val expirationDate = "12/24"
        val cvv = "123"

        mockedCardValidator = CardValidatorMockImpl(
            isCardNumberValid = true,
            isCardHolderValid = false,
            isExpirationDateValid = true,
            isCvvValid = true
        )

        cardFormViewModel = CardFormViewModel(
            mockedCardValidator
        )

        cardFormViewModel.onCardNumberChanged(cardNumber)
        cardFormViewModel.onExpirationDateChanged(expirationDate)
        cardFormViewModel.onCvvChanged(cvv)
        cardFormViewModel.onCardHolderChanged(cardHolder)

        cardFormViewModel.submit()

        val state = cardFormViewModel.state

        assertTrue(state.submittedSuccessfully == false)
    }

    @Test
    fun `when card expiration date is invalid submit failed`() {
        val cardNumber = "123 5678 9012 3456"
        val cardHolder = "John Snow"
        val expirationDate = "12/24"
        val cvv = "123"

        mockedCardValidator = CardValidatorMockImpl(
            isCardNumberValid = true,
            isCardHolderValid = true,
            isExpirationDateValid = false,
            isCvvValid = true
        )

        cardFormViewModel = CardFormViewModel(
            mockedCardValidator
        )

        cardFormViewModel.onCardNumberChanged(cardNumber)
        cardFormViewModel.onExpirationDateChanged(expirationDate)
        cardFormViewModel.onCvvChanged(cvv)
        cardFormViewModel.onCardHolderChanged(cardHolder)

        cardFormViewModel.submit()

        val state = cardFormViewModel.state

        assertTrue(state.submittedSuccessfully == false)
    }

    @Test
    fun `when card cvv is invalid submit failed`() {
        val cardNumber = "123 5678 9012 3456"
        val cardHolder = "John Snow"
        val expirationDate = "12/24"
        val cvv = "123"

        mockedCardValidator = CardValidatorMockImpl(
            isCardNumberValid = true,
            isCardHolderValid = true,
            isExpirationDateValid = true,
            isCvvValid = false
        )

        cardFormViewModel = CardFormViewModel(
            mockedCardValidator
        )

        cardFormViewModel.onCardNumberChanged(cardNumber)
        cardFormViewModel.onExpirationDateChanged(expirationDate)
        cardFormViewModel.onCvvChanged(cvv)
        cardFormViewModel.onCardHolderChanged(cardHolder)

        cardFormViewModel.submit()

        val state = cardFormViewModel.state

        assertTrue(state.submittedSuccessfully == false)
    }
}