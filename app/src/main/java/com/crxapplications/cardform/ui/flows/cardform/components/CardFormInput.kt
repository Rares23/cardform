package com.crxapplications.cardform.ui.flows.cardform.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import com.crxapplications.cardform.ui.core.utils.UiText
import com.crxapplications.cardform.ui.theme.disabled

@Composable
fun CardFormInput(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isFocused: Boolean = false,
    keyboardOptions: KeyboardOptions,
    maxLength: Int,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
    visualTransformation: ((AnnotatedString) -> TransformedText)? = null,
    error: UiText? = null,
) {
    val context = LocalContext.current

    val textColor = when {
        error != null -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.onBackground
        else -> MaterialTheme.colorScheme.disabled
    }

    Column(
        modifier = modifier.padding(
            vertical = 16.dp,
        ),
    ) {
        Text(
            text = label.uppercase(), style = MaterialTheme.typography.bodyLarge.copy(
                color = textColor
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(
            modifier = Modifier.focusRequester(
                focusRequester,
            ),
            value = value,
            keyboardOptions = keyboardOptions,
            maxLines = 1,
            minLines = 1,
            visualTransformation = {
                visualTransformation?.invoke(it) ?: TransformedText(it, OffsetMapping.Identity)
            },
            textStyle = MaterialTheme.typography.titleLarge,
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
        )
        Text(
            text = error?.asString(context) ?: "",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.error
            )
        )
    }
}