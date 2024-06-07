package com.crxapplications.cardform.ui.core.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class ResourceString(@StringRes val resId: Int) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is ResourceString -> {
                context.getString(resId)
            }
        }
    }
}