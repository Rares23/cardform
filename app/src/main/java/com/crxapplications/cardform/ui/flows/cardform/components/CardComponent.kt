package com.crxapplications.cardform.ui.flows.cardform.components

import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutElastic
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import com.crxapplications.cardform.R
import com.crxapplications.cardform.ui.core.components.SliceShape
import com.crxapplications.cardform.ui.flows.cardform.viewmodels.CardType
import com.crxapplications.cardform.ui.theme.CardFormTheme
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin

val cardWidthSize = 320.dp
val cardHeightSize = 200.dp

@Composable
fun CardComponent(
    modifier: Modifier = Modifier,
    cardNumber: String = "",
    cardHolder: String = "",
    expirationDate: String = "",
    cvv: String = "",
    cardType: CardType = CardType.UNKNOWN,
    showBackSide: Boolean = false,
) {
    val animationTime = 5000
    var rotated by remember { mutableStateOf(showBackSide) }

    val easing = Easing { fraction ->
        val c4 = (2f * PI) / 3f

        return@Easing when (fraction) {
            0f -> 0f
            1f -> 1f
            else -> {
                val amplitude = 8f
                (amplitude.pow(-10.0f * fraction) *
                        sin((fraction * 10f - 0.75f) * c4) + 1f).toFloat()
            }

        }
    }

    val rotationFront by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(animationTime, easing = easing), label = "rotationFront",
    )

    val rotationBack by animateFloatAsState(
        targetValue = if (rotated) 0f else -180f,
        animationSpec = tween(animationTime, easing = easing), label = "rotationBack"
    )
    val zIndexFront by animateFloatAsState(
        targetValue = if (!rotated) 10f else 0f,
        animationSpec = tween(animationTime, easing = easing),
        label = "zIndexFront"
    )
    val zIndexBack by animateFloatAsState(
        targetValue = if (rotated) 10f else 0f,
        animationSpec = tween(animationTime, easing = easing),
        label = "zIndexBack"
    )

    LaunchedEffect(showBackSide) {
        rotated = showBackSide
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color.LightGray.copy(
                    alpha = 0.25f
                )
            ),
    ) {
        FrontCard(
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(zIndexFront)
                .graphicsLayer {
                    rotationY = rotationFront
                    cameraDistance = 40f * density
                },
            cardNumber = cardNumber,
            cardHolder = cardHolder,
            expirationDate = expirationDate,
            cardType = cardType
        )

        BackCard(
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(zIndexBack)
                .graphicsLayer {
                    rotationY = rotationBack
                    cameraDistance = 40f * density
                },
            cvv = cvv,
            cardType = cardType
        )
    }
}

@Composable
fun FrontCard(
    modifier: Modifier = Modifier,
    cardNumber: String = "",
    cardHolder: String = "",
    expirationDate: String = "",
    cardType: CardType,
) {
    val cardNumberColor = if (cardNumber.isEmpty()) Color.Gray else Color.White
    val cardHolderColor = if (cardHolder.isEmpty()) Color.Gray else Color.White
    val expirationDateColor = if (expirationDate.isEmpty()) Color.Gray else Color.White

    var switchCardFace by remember { mutableStateOf(cardType != CardType.UNKNOWN) }
    var lastCardFace by remember { mutableStateOf(CardType.UNKNOWN) }

    val clipPercentAnim by animateFloatAsState(
        targetValue = if (switchCardFace) 1f else 0f,
        animationSpec = tween(500), label = "clipPercentAnim",
        finishedListener = {
            lastCardFace = cardType
        }
    )


    LaunchedEffect(cardType) {
        switchCardFace = cardType != CardType.UNKNOWN

        if (switchCardFace) {
            lastCardFace = cardType
        }
    }

    Box(
        modifier = modifier
            .size(
                width = cardWidthSize,
                height = cardHeightSize,
            ),
    ) {
        Image(
            modifier = Modifier
                .size(
                    width = cardWidthSize,
                    height = cardHeightSize,
                ),
            painter = painterResource(id = lastCardFace.cardFrontImageRes()),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )

        Image(
            modifier = Modifier
                .size(
                    width = cardWidthSize,
                    height = cardHeightSize,
                )
                .clip(
                    SliceShape(
                        clipPercent = clipPercentAnim,
                    )
                ),
            painter = painterResource(id = R.drawable.card_placeholder),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = cardNumber.ifEmpty { stringResource(id = R.string.card_number_placeholder) }
                .uppercase().chunked(4).joinToString(" "),
            style = MaterialTheme.typography.titleLarge.copy(
                color = cardNumberColor,
            ),
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
            text = expirationDate.ifEmpty { stringResource(id = R.string.card_expiration_placeholder) }
                .uppercase().chunked(2).joinToString("/"),
            style = MaterialTheme.typography.titleSmall.copy(
                color = expirationDateColor,
            ),
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 16.dp, start = 16.dp),
            text = cardHolder.ifEmpty { stringResource(id = R.string.card_holder_placeholder) }
                .uppercase(),
            style = MaterialTheme.typography.titleSmall.copy(
                color = cardHolderColor,
            ),
        )
    }
}

@Composable
fun BackCard(
    modifier: Modifier = Modifier,
    cvv: String = "",
    cardType: CardType,
) {

    var switchCardFace by remember { mutableStateOf(cardType != CardType.UNKNOWN) }

    val clipPercentAnim by animateFloatAsState(
        targetValue = if (switchCardFace) 1f else 0f,
        animationSpec = tween(500), label = "clipPercentAnim"
    )

    LaunchedEffect(cardType) {
        switchCardFace = cardType != CardType.UNKNOWN
    }

    Box(
        modifier = modifier
            .size(
                width = cardWidthSize,
                height = cardHeightSize,
            ),
    ) {
        Image(
            modifier = Modifier.size(
                width = cardWidthSize,
                height = cardHeightSize,
            ),
            painter = painterResource(id = cardType.cardBackImageRes()),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )

        Image(
            modifier = Modifier
                .size(
                    width = cardWidthSize,
                    height = cardHeightSize,
                )
                .clip(
                    SliceShape(
                        clipPercent = clipPercentAnim,
                    )
                ),
            painter = painterResource(id = R.drawable.card_placeholder),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            text = cvv.ifEmpty { "   " }.uppercase(),
            maxLines = 1,
            minLines = 1,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Black,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardFormEmptyScreenPreview() {
    CardFormTheme {
        CardComponent()
    }
}

@Preview(showBackground = true)
@Composable
fun CardFormFullDataScreenPreview() {
    CardFormTheme {
        CardComponent(
            cardNumber = "1234 1234 1234 1234",
            cardHolder = "John Doe",
            expirationDate = "12/23",
            cvv = "123",
            cardType = CardType.VISA,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardFormFullDataBackScreenPreview() {
    CardFormTheme {
        CardComponent(
            cardNumber = "1234 1234 1234 1234",
            cardHolder = "John Doe",
            expirationDate = "12/23",
            cvv = "123",
            showBackSide = true,
            cardType = CardType.VISA,
        )
    }
}