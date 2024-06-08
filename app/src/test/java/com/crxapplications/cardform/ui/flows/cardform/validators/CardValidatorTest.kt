package com.crxapplications.cardform.ui.flows.cardform.validators

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import android.icu.util.Calendar

class CardValidatorTest {

    private lateinit var calendar: Calendar

    private val cardValidator: CardValidator = CardValidatorImpl()

    @BeforeEach
    fun beforeStart() {
        calendar = mockk(relaxed = true)
        mockkStatic(Calendar::class)

        every { Calendar.getInstance() } returns calendar
    }

    @Test
    fun `when card number invalid return error`() {
        val cardNumber = "123"
        val result = cardValidator.validateCardNumber(cardNumber)
        assert(result != null)
    }

    @Test
    fun `when card number valid return null`() {
        val cardNumber = "1234 5678 9012 3456"
        val result = cardValidator.validateCardNumber(cardNumber)
        assert(result == null)
    }

    @Test
    fun `when card holder is empty return error`() {
        val cardHolder = "Ab"
        val result = cardValidator.validateCardHolder(cardHolder)
        assert(result != null)
    }

    @Test
    fun `when card holder is valid return null`() {
        val cardHolder = "John Snow"
        val result = cardValidator.validateCardHolder(cardHolder)
        assert(result == null)
    }

    @Test
    fun `when card is expired return error`() {

        every {
            calendar.get(Calendar.YEAR)
        } returns 2024

        every {
            calendar.get(Calendar.MONTH)
        } returns 1

        val expirationDate = "01/23"
        val result = cardValidator.validateExpirationDate(expirationDate)
        assert(result != null)
    }

    @Test
    fun `when card is valid return null`() {

        every {
            calendar.get(Calendar.YEAR)
        } returns 2024

        every {
            calendar.get(Calendar.MONTH)
        } returns 1

        val expirationDate = "01/25"
        val result = cardValidator.validateExpirationDate(expirationDate)
        assert(result == null)
    }

    @Test
    fun `when cvv is invalid return error`() {
        val cvv = "12"
        val result = cardValidator.validateCvv(cvv)
        assert(result != null)
    }

    @Test
    fun `when cvv is valid return null`() {
        val cvv = "123"
        val result = cardValidator.validateCvv(cvv)
        assert(result == null)
    }
}