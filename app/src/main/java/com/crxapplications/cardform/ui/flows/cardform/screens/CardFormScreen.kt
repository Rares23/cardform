package com.crxapplications.cardform.ui.flows.cardform.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.crxapplications.cardform.ui.core.components.BaseScaffold
import com.crxapplications.cardform.ui.flows.cardform.components.CardComponent
import com.crxapplications.cardform.ui.flows.cardform.components.CardFormComponent
import com.crxapplications.cardform.ui.flows.cardform.viewmodels.CardFormActions
import com.crxapplications.cardform.ui.flows.cardform.viewmodels.CardFormState
import com.crxapplications.cardform.ui.theme.CardFormTheme

@Composable
fun CardFormScreen(
    state: CardFormState,
    actions: CardFormActions,
) {
    val context = LocalContext.current
    LaunchedEffect(state.message) {
        state.message?.let {
            Toast.makeText(context, it.asString(context), Toast.LENGTH_SHORT).show()
            actions.resetMessage()
        }
    }

    BaseScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
        ) {
            CardComponent(
                modifier = Modifier.weight(1f),
                cardType = state.cardType,
                showBackSide = state.showBackSide,
                cardNumber = state.cardNumber.value,
                cardHolder = state.cardHolder.value,
                expirationDate = state.expirationDate.value,
                cvv = state.cvv.value,
            )

            CardFormComponent(
                submit = actions::submit,
                cardNumber = state.cardNumber,
                onCardNumberChange = actions::onCardNumberChanged,
                cardExpiration = state.expirationDate,
                onCardExpirationChange = actions::onExpirationDateChanged,
                cardCvv = state.cvv,
                onCardCvvChange = actions::onCvvChanged,
                cardHolder = state.cardHolder,
                onCardHolderChange = actions::onCardHolderChanged,
                onSwitchCard = actions::switchCard,
            )
        }
    }
}