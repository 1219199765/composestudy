package com.common.compose14.view

import QureytoImageShapes
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.common.compose14.R
import com.common.compose14.ui.theme.CommonBg
import com.common.compose14.ui.theme.Gray1
import com.common.compose14.ui.theme.Gray3
import com.common.compose14.ui.theme.Red1

@Composable
fun UserView() {

    UserViewRoot()
}

@Composable
fun UserViewRoot() {
    val statusBarHeight = LocalDensity.current.run { WindowInsets.statusBars.getTop(this).toDp() }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(CommonBg)
            .verticalScroll(rememberScrollState())
    ) {
        val (bg1, bg2, avatar, name, desc, icon1, icon2) = createRefs()

        val (txt1, txt2, txt3, txt4) = createRefs()
        val (order, fuwu, title, tuijian) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(QureytoImageShapes(160f))
                .background(MaterialTheme.colorScheme.primary)
        )
        Image(
            painter = painterResource(id = R.drawable.topbgbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(QureytoImageShapes(160f))
                .constrainAs(bg1) {}
        )

        Image(painter = painterResource(id = R.drawable.avatar), contentDescription = null,
            modifier = Modifier
                .size(55.dp)
                .border(
                    BorderStroke(1.dp, Color.White),
                    CircleShape
                )
                .clip(CircleShape)
                .constrainAs(avatar) {
                    start.linkTo(parent.start, 17.dp)
                    top.linkTo(parent.top, 17.dp + statusBarHeight)
                })
        Text(text = "丢丢", style = MaterialTheme.typography.bodyLarge.copy(Color.White),
            modifier = Modifier.constrainAs(name) {
                top.linkTo(avatar.top)
                start.linkTo(avatar.end, 15.dp)
                bottom.linkTo(desc.top)
            })

        Text(text = "158****0000", style = MaterialTheme.typography.labelMedium.copy(Color.White),
            modifier = Modifier.constrainAs(desc) {
                top.linkTo(name.bottom)
                start.linkTo(avatar.end, 15.dp)
                bottom.linkTo(avatar.bottom)
            })

        Icon(painter = painterResource(id = R.drawable.usersetting),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .constrainAs(icon1) {
                    top.linkTo(avatar.top)
                    end.linkTo(icon2.start, 10.dp)
                })

        Icon(painter = painterResource(id = R.drawable.kf),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .constrainAs(icon2) {
                    top.linkTo(avatar.top)
                    end.linkTo(parent.end, 15.dp)
                })

        createHorizontalChain(txt1, txt2, txt3, txt4, chainStyle = ChainStyle.Spread)

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .height(42.dp)
            .constrainAs(txt1) {
                top.linkTo(avatar.bottom, 20.dp)
                start.linkTo(parent.start)
                end.linkTo(txt2.start)
            }) {
            Text(text = "51", fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "余额(元)", fontSize = 12.sp, color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .height(42.dp)
            .constrainAs(txt2) {
                top.linkTo(avatar.bottom, 20.dp)
                start.linkTo(txt1.end)
                end.linkTo(txt3.start)
            }) {
            Text(text = "51", fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "优惠券", fontSize = 12.sp, color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .height(42.dp)
            .constrainAs(txt3) {
                top.linkTo(avatar.bottom, 20.dp)
                start.linkTo(txt2.end)
                end.linkTo(txt4.start)
            }) {
            Text(text = "51", fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "电子券", fontSize = 12.sp, color = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .height(42.dp)
            .constrainAs(txt4) {
                top.linkTo(avatar.bottom, 20.dp)
                start.linkTo(txt3.end)
                end.linkTo(parent.end)
            }) {
            Text(text = "51", fontSize = 18.sp, color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "积分", fontSize = 12.sp, color = Color.White)
        }

        UserOrder(modifier = Modifier.constrainAs(order) {
            top.linkTo(txt1.bottom, 20.dp)
        })

        UserFuwu(modifier = Modifier.constrainAs(fuwu) {
            top.linkTo(order.bottom, 20.dp)
        })

        UserTitle(modifier = Modifier.constrainAs(title) {
            top.linkTo(fuwu.bottom, 30.dp)
        })

        UserTuijian(modifier = Modifier.constrainAs(tuijian) {
            top.linkTo(title.bottom, 30.dp)
        })

    }
}

@Composable
fun UserOrder(modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .fillMaxWidth()
            .height(137.dp)
            .padding(start = 15.dp, top = 16.dp, end = 15.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "我的订单", style = MaterialTheme.typography.bodySmall.copy(Gray1))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "全部订单", style = MaterialTheme.typography.labelMedium.copy(Gray3))
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Rounded.ArrowForward, contentDescription = null,
                modifier = Modifier.size(10.dp)
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {
            CarOrderItem("代付款", R.drawable.user1)
            CarOrderItem("待发货", R.drawable.user2)
            CarOrderItem("待收货", R.drawable.user3)
            CarOrderItem("待评价", R.drawable.user4)
            CarOrderItem("退款/售后", R.drawable.user5)
        }
    }
}

@Composable
fun CarOrderItem(des: String, img: Int) {
    ConstraintLayout(
        modifier = Modifier
            .height(49.dp)
            .wrapContentWidth()
    ) {
        val (num, icon, txt) = createRefs()
        Icon(painter = painterResource(id = img), contentDescription = null,
            modifier = Modifier
                .size(26.dp)
                .constrainAs(icon) {
                    top.linkTo(num.bottom, (-5).dp)
                    end.linkTo(num.end, 5.dp)
                })
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(14.dp)
                .background(Red1)
                .constrainAs(num) {
                    end.linkTo(parent.end)
                }, contentAlignment = Alignment.Center
        ) {
            Text(text = "1", color = Color.White, fontSize = 10.sp)
        }
        Text(text = des, color = Gray3, fontSize = 11.sp, modifier = Modifier.constrainAs(
            txt
        ) {
            top.linkTo(icon.bottom, 10.dp)
            centerHorizontallyTo(icon)
        })
    }
}


@Composable
fun UserFuwu(modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .fillMaxWidth()
            .height(137.dp)
            .padding(start = 15.dp, top = 16.dp, end = 15.dp)
    ) {
        Text(text = "我的服务", style = MaterialTheme.typography.bodySmall.copy(Gray1))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {
            UserFuwuItem("收货地址", R.drawable.user_1)
            UserFuwuItem("足迹", R.drawable.user_2)
            UserFuwuItem("我的收藏", R.drawable.user_3)
            UserFuwuItem("服务中心", R.drawable.user_4)
            UserFuwuItem("在线客服", R.drawable.user_5)
        }
    }
}

@Composable
fun UserFuwuItem(des: String, img: Int) {
    Column(
        modifier = Modifier
            .height(49.dp)
            .wrapContentWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = img), contentDescription = null,
            modifier = Modifier
                .size(30.dp), contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = des, color = Gray3, fontSize = 11.sp)
    }
}

@Composable
fun UserTitle(modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.tit_icon_l), contentDescription = null)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "为你推荐", style = MaterialTheme.typography.bodyMedium.copy(Gray1))
        Spacer(modifier = Modifier.width(10.dp))
        Image(painter = painterResource(id = R.drawable.tit_icon_r), contentDescription = null)
    }
}

@Composable
fun UserTuijian(modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .width(167.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    Color.White
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar), contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(137.dp), contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "云南昆明 有机水果胡萝卜 1.5kg/份",
                style = MaterialTheme.typography.labelLarge.copy(
                    Gray1
                ),
                maxLines = 2,
                overflow = TextOverflow.Clip
            )
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .width(167.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    Color.White
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar), contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(137.dp), contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "云南昆明 有机水果胡萝卜 1.5kg/份",
                style = MaterialTheme.typography.labelLarge.copy(
                    Gray1
                ),
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
    }
}


@Preview
@Composable
fun xx() {
    UserViewRoot()
}