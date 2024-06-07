package com.crxapplications.cardform.ui.flows.cardform.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import com.crxapplications.cardform.R
import com.crxapplications.cardform.ui.core.utils.cardExpiryTransform
import com.crxapplications.cardform.ui.core.utils.cardNumberTransform
import com.crxapplications.cardform.ui.flows.cardform.viewmodels.CardInputData
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardFormComponent(
    cardNumber: CardInputData,
    onCardNumberChange: (String) -> Unit,
    cardExpiration: CardInputData,
    onCardExpirationChange: (String) -> Unit,
    cardCvv: CardInputData,
    onCardCvvChange: (String) -> Unit,
    cardHolder: CardInputData,
    onCardHolderChange: (String) -> Unit,
    onSwitchCard: (Boolean) -> Unit,
    submit: () -> Unit,
) {
    val cardNumberFocusRequester = remember { FocusRequester() }
    val cardExpirationFocusRequester = remember { FocusRequester() }
    val cardCvvFocusRequester = remember { FocusRequester() }
    val cardHolderFocusRequester = remember { FocusRequester() }

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {
        4
    })

    val lastPage = pagerState.currentPage == pagerState.pageCount - 1

    var showBack by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState.currentPage) {
        var switch = false
        when (pagerState.currentPage) {
            0 -> {
                cardNumberFocusRequester.requestFocus()
                showBack = false
            }

            1 -> {
                cardExpirationFocusRequester.requestFocus()
                showBack = true
            }

            2 -> {
                cardCvvFocusRequester.requestFocus()
                switch = true
                showBack = true
            }

            3 -> {
                cardHolderFocusRequester.requestFocus()
                showBack = true
            }
        }

        onSwitchCard(switch)
    }

    Column {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(end = 120.dp, start = 16.dp),
        ) { page ->
            when (page) {
                0 -> {
                    CardFormInput(
                        modifier = Modifier.width(IntrinsicSize.Max),
                        label = stringResource(id = R.string.card_number_input_label),
                        value = cardNumber.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        maxLength = 16,
                        onValueChange = onCardNumberChange,
                        isFocused = pagerState.currentPage == 0,
                        focusRequester = cardNumberFocusRequester,
                        visualTransformation = {
                            cardNumberTransform(it.text)
                        },
                        error = cardNumber.error,
                    )
                }

                1 -> {
                    CardFormInput(
                        modifier = Modifier.width(IntrinsicSize.Max),
                        label = stringResource(id = R.string.card_expiration_input_label),
                        value = cardExpiration.value,
                        maxLength = 4,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = onCardExpirationChange,
                        isFocused = pagerState.currentPage == 1,
                        focusRequester = cardExpirationFocusRequester,
                        visualTransformation = {
                            cardExpiryTransform(it.text)
                        },
                        error = cardExpiration.error,
                    )
                }

                2 -> {
                    CardFormInput(
                        modifier = Modifier.width(IntrinsicSize.Max),
                        label = stringResource(id = R.string.card_cvv_input_label),
                        value = cardCvv.value,
                        maxLength = 3,
                        isFocused = pagerState.currentPage == 2,
                        focusRequester = cardCvvFocusRequester,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = onCardCvvChange,
                        error = cardCvv.error,
                    )
                }

                3 -> {
                    CardFormInput(
                        modifier = Modifier.width(IntrinsicSize.Max),
                        label = stringResource(id = R.string.card_holder_input_label),
                        value = cardHolder.value,
                        maxLength = 20,
                        isFocused = pagerState.currentPage == 3,
                        focusRequester = cardHolderFocusRequester,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = onCardHolderChange,
                        error = cardHolder.error,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AnimatedVisibility(visible = showBack) {
                CardFormButton(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    label = stringResource(id = R.string.card_form_back_button),
                    onPress = {
                        scope.launch {
                            var backPage = pagerState.currentPage - 1
                            if (backPage < 0) {
                                backPage = 0
                            }
                            pagerState.animateScrollToPage(backPage)
                        }
                    }
                )
            }
            CardFormButton(
                modifier = Modifier.weight(weight = 0.5f, fill = true),
                label = if (lastPage) stringResource(id = R.string.card_form_submit_button) else stringResource(
                    id = R.string.card_form_next_button
                ),
                onPress = {
                    if (lastPage) {
                        submit()
                    } else {
                        scope.launch {
                            var nextPage = pagerState.currentPage + 1
                            if (nextPage > pagerState.pageCount) {
                                nextPage = pagerState.pageCount
                            }
                            pagerState.animateScrollToPage(nextPage)
                        }
                    }

                }
            )
        }
    }
}