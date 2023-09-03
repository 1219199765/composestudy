import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.commandiron.compose_loading.FadingCircle
import com.common.compose14.R
import com.common.compose14.ui.theme.CommonBg
import com.loren.component.view.composesmartrefresh.SmartSwipeStateFlag
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun NoRIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    // 这也是源码的一部分
    val iconButtonSizeModifier = Modifier.size(48.dp)
    Box(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null
            )
            .then(iconButtonSizeModifier),
        contentAlignment = Alignment.Center
    ) { content() }
}

@Composable
fun NoImage(painter: Painter, padding: Int = 0, shape: Int = 0, onClick: () -> Unit) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                })
            .padding(padding.dp)
            .clip(RoundedCornerShape(shape))
    )
}

@Composable
fun CommonLoading(bg: Color = Color.White) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = bg), contentAlignment = Alignment.Center
    ) {
//        CircularProgressIndicator()
        FadingCircle()
    }
}


@Composable
fun CommonErrorView(refresh: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = R.drawable.bg_error), contentDescription = null,
            modifier = Modifier.clickable {
                refresh()
            })
    }
}

@Composable
fun CommonEmptyView(refresh: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = R.drawable.bg_empty), contentDescription = null,
            modifier = Modifier.clickable {
                refresh()
            })
    }
}


@Composable
fun BoxTxt(modifier: Modifier = Modifier, des: String) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Text(text = des, color = MaterialTheme.colorScheme.primary)
    }
}

//@Composable
//fun CusCircularProgressIndicator(percent: Float, size: Int, modifier: Modifier = Modifier) {
//    Box(
//        modifier
//            .size(size.dp)
//            .clip(CircleShape)
//            .background(Color.White)
//            .padding(1.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        CircularProgressIndicator(
//            modifier = Modifier.fillMaxSize(),
//            progress = 1f,
//            strokeWidth = 4.dp,
//            strokeCap = StrokeCap.Round,
//            color = CommonBg
//        )
//        CircularProgressIndicator(
//            modifier = Modifier.fillMaxSize(),
//            progress = percent,
//            strokeWidth = 4.dp,
//            strokeCap = StrokeCap.Round,
//            color = MaterialTheme.colorScheme.primary
//        )
//        Text(
//            text = "${(percent * 100).toInt()} %",
//            color = MaterialTheme.colorScheme.primary,
//            fontSize = 13.sp
//        )
//    }
//
//}


@Composable
fun MyRefreshHeader2(flag: SmartSwipeStateFlag, isNeedTimestamp: Boolean = true) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CommonLoading(CommonBg)
        }
    }
}


@Stable
class QureytoImageShapes(var hudu: Float = 100f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(0f, size.height - hudu)
        path.quadraticBezierTo(size.width / 2f, size.height, size.width, size.height - hudu)
        path.lineTo(size.width, 0f)
        path.close()
        return Outline.Generic(path)
    }
}

@Composable
fun CommonPayDialog(onClick: () -> Unit) {
    Dialog(
        onDismissRequest = { onClick.invoke() }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .height(200.dp)
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pay1), contentDescription = null,
                    modifier = Modifier.size(80.dp), contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.pay2), contentDescription = null,
                    modifier = Modifier.size(80.dp), contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun BottomSheetDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    cancelable: Boolean = true,
    canceledOnTouchOutside: Boolean = true,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    BackHandler(enabled = visible, onBack = {
        if (cancelable) {
            onDismissRequest()
        }
    })
    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 400, easing = LinearEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 400, easing = LinearEasing))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0x99000000))
                    .clickable {
                        if (canceledOnTouchOutside) {
                            onDismissRequest()
                        }
                    }
            )
        }
        InnerDialog(visible, cancelable, onDismissRequest, content)
    }
}

@Composable
private fun BoxScope.InnerDialog(
    visible: Boolean,
    cancelable: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    var offsetY by remember {
        mutableStateOf(0f)
    }
    val offsetYAnimate by animateFloatAsState(targetValue = offsetY)
    var bottomSheetHeight by remember { mutableStateOf(0f) }
    AnimatedVisibility(
        modifier = Modifier
            .clickableNoRipdple {

            }
            .align(alignment = Alignment.BottomCenter)
            .onGloballyPositioned {
                bottomSheetHeight = it.size.height.toFloat()
            }
            .offset(offset = {
                IntOffset(0, offsetYAnimate.roundToInt())
            })
            .draggable(
                state = rememberDraggableState(
                    onDelta = {
                        offsetY = (offsetY + it.toInt()).coerceAtLeast(0f)
                    }
                ),
                orientation = Orientation.Vertical,
                onDragStarted = {
                },
                onDragStopped = {
                    if (cancelable && offsetY > bottomSheetHeight / 2) {
                        onDismissRequest()
                    } else {
                        offsetY = 0f
                    }
                }
            ),
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            initialOffsetY = { 2 * it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            targetOffsetY = { it }
        ),
    ) {
        content()
    }
}


// fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier =
//    composed {
//        clickable(
//            indication = null,
//            interactionSource = remember { MutableInteractionSource() }) {
//
//        }
//    }

fun Modifier.clickableNoRipdple(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
