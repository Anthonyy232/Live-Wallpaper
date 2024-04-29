package com.anthonyla.paperize.feature.wallpaper.presentation.licenses_screen.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.anthonyla.paperize.R

/**
 * Title for the license screen.
 */
@Composable
fun LicenseTitle(
    largeTopAppBarHeight: Dp,
    smallTopAppBarHeight: Dp,
    paddingMedium: Dp,
    modifier: Modifier = Modifier,
    offset: State<Int>
) {
    val titlePaddingStart = 16.dp
    val titlePaddingEnd = 64.dp
    val titleFontScaleStart = 1f
    val titleFontScaleEnd = 0.7f
    var titleHeightPx by remember { mutableFloatStateOf(0f) }
    var titleWidthPx by remember { mutableFloatStateOf(0f) }

    Text(
        text = stringResource(R.string.licenses),
        style = MaterialTheme.typography.headlineMedium,
        fontSize = 30.sp,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .graphicsLayer {
                val collapseRange: Float = (largeTopAppBarHeight.toPx() - smallTopAppBarHeight.toPx())
                val collapseFraction: Float = (offset.value.toFloat() / collapseRange).coerceIn(0f, 1f)

                val scaleXY = lerp(
                    titleFontScaleStart.dp,
                    titleFontScaleEnd.dp,
                    collapseFraction
                )

                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f

                val titleYFirstInterpolatedPoint = lerp(
                    largeTopAppBarHeight - titleHeightPx.toDp() - paddingMedium,
                    largeTopAppBarHeight / 2,
                    collapseFraction
                )

                val titleXFirstInterpolatedPoint = lerp(
                    titlePaddingStart,
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    collapseFraction
                )

                val titleYSecondInterpolatedPoint = lerp(
                    largeTopAppBarHeight / 2,
                    (smallTopAppBarHeight - titleHeightPx.toDp() / 2) - 5.dp,
                    collapseFraction
                )

                val titleXSecondInterpolatedPoint = lerp(
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    titlePaddingEnd - titleExtraStartPadding,
                    collapseFraction
                )

                val titleY = lerp(
                    titleYFirstInterpolatedPoint,
                    titleYSecondInterpolatedPoint,
                    collapseFraction
                )

                val titleX = lerp(
                    titleXFirstInterpolatedPoint,
                    titleXSecondInterpolatedPoint,
                    collapseFraction
                )

                translationY = titleY.toPx()
                translationX = titleX.toPx()
                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()
            }
    )
}