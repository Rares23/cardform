package com.crxapplications.cardform.ui.flows.cardform.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.crxapplications.cardform.ui.core.components.BaseScaffold
import com.crxapplications.cardform.ui.flows.cardform.components.CardComponent
import com.crxapplications.cardform.ui.flows.cardform.viewmodels.CardFormActions
import com.crxapplications.cardform.ui.flows.cardform.viewmodels.CardFormState
import com.crxapplications.cardform.ui.flows.cardform.viewmodels.CardType
import com.crxapplications.cardform.ui.theme.CardFormTheme

@Composable
fun CardFormScreen(
    state: CardFormState,
    actions: CardFormActions,
) {
    var rotate by remember { mutableStateOf(false) }
    var cardType by remember { mutableStateOf(CardType.UNKNOWN) }

    BaseScaffold {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CardComponent(
                modifier = Modifier.weight(1f),
                cardType = cardType,
                showBackSide = rotate,
            )

            //For testing
//            Button(
//                onClick = {rotate = !rotate},
//            ) {
//                Text("Rotate")
//            }
//
//            Button(
//                onClick = {
//                          cardType = if(cardType == CardType.UNKNOWN) CardType.VISA else CardType.UNKNOWN
//                },
//            ) {
//                Text("Card Type")
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardFormScreenPreview() {
    CardFormTheme {
        CardFormScreen(state = CardFormState(), actions = object : CardFormActions {
            override fun onCardNumberChanged(cardNumber: String) {
                // no-op
            }

            override fun onCardHolderChanged(cardHolder: String) {
                // no-op
            }

            override fun onExpirationDateChanged(expirationDate: String) {
                // no-op
            }

            override fun onCvvChanged(cvv: String) {
                // no-op
            }
        })
    }
}