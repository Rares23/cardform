package com.crxapplications.cardform.ui.core.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

fun cardNumberTransform(cardNumber: String): TransformedText {
    val offsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 8) return offset + 1
            if (offset <= 12) return offset + 2
            if (offset <= 16) return offset + 3
            return 16
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 8) return offset - 1
            if (offset <= 12) return offset - 2
            if (offset <= 16) return offset - 3
            return offset - 16
        }
    }

    return TransformedText(
        AnnotatedString(cardNumber.chunked(4).joinToString(" ")),
        offsetTranslator
    )
}

fun cardExpiryTransform(expiryDate: String) : TransformedText {
    val offsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 4) return offset + 1
            return offset
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 4) return offset - 1
            return offset - 4
        }
    }

    return TransformedText(
        AnnotatedString(expiryDate.chunked(2).joinToString("/")),
        offsetTranslator
    )
}