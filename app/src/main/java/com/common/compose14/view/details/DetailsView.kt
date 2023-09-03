package com.common.compose14.view.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.common.compose14.R
import com.common.compose14.ui.theme.CommonBg
import com.common.compose14.ui.theme.Gray1
import com.common.compose14.ui.theme.Gray2
import com.common.compose14.ui.theme.Gray3
import com.common.compose14.ui.theme.Gray4
import com.common.compose14.ui.theme.Gray5
import com.common.compose14.ui.theme.Red1
import com.common.compose14.ui.theme.Yellow1

@Composable
fun DetailsView(id: String) {
    DetailsRoot()
}

@Composable
fun DetailsRoot() {
    Column(
        Modifier
            .fillMaxSize()
            .background(CommonBg)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DetailsTopBar()
        Image(
            painter = painterResource(id = R.drawable.origin), contentDescription = null,
            modifier = Modifier.size(240.dp)
        )
        DetailsItem1()
        DetailsItem2()
        DetailsItem3()
        DetailsItem4()
        DetailsItem6()
        DetailsItem7()
        DetailsItem5()
    }
}

@Composable
fun DetailsTopBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.details2), contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.details1), contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.car), contentDescription = null,
            modifier = Modifier.size(24.dp)
        )


    }
}

@Composable
fun DetailsItem1() {
    Column(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(135.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = "￥", style = MaterialTheme.typography.labelSmall.copy(Red1),
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = "39.9",
                fontSize = 21.sp,
                color = Red1, modifier = Modifier.alignByBaseline()
            )
            Text(
                text = "/份", fontSize = 11.sp,
                color = Gray2, modifier = Modifier.alignByBaseline()
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "39.9", fontSize = 11.sp,
                color = Gray3, modifier = Modifier.alignByBaseline(),
                textDecoration = TextDecoration.LineThrough
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "入口即化",
                fontSize = 11.sp,
                color = Gray3,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Gray3,
                        shape = RoundedCornerShape(3.dp)
                    )
                    .padding(horizontal = 3.dp, vertical = 1.dp)
                    .alignByBaseline()
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "好吃不腻",
                fontSize = 11.sp,
                color = Gray3,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Gray3,
                        shape = RoundedCornerShape(3.dp)
                    )
                    .padding(horizontal = 3.dp, vertical = 1.dp)
                    .alignByBaseline()
            )
        }
        Text(
            text = "四川眉山 爱媛38号可吸的果冻橙礼盒装12粒(单果180g+)", fontSize = 18.sp,
            modifier = Modifier.padding(top = 5.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "产地量贩",
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(3.dp)
                    )
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 3.dp, vertical = 2.dp),
                color = Color.White,
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "基地优选 售后无忧", fontSize = 11.sp, color = Gray2)
        }
    }
}

@Composable
fun DetailsItem2() {
    Column(
        Modifier
            .fillMaxWidth()
            .height(95.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                Color.White
            )
            .padding(10.dp), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(text = "配送", color = Gray3, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "上架24H发货，第三方物流配送，免运费 ",
                color = Gray1,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
        Row {
            Text(text = "服务", color = Gray3, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "品质保证，生鲜不支持7天无理由退货 ",
                color = Gray1,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
        Row {
            Text(text = "优惠", color = Gray3, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "特价商品每人限购2份", color = Gray1, fontSize = 12.sp, maxLines = 1)
        }
    }
}

@Composable
fun DetailsItem3() {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "已选", color = Gray1, fontSize = 13.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "0.5元/份", color = Gray2, fontSize = 12.sp)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Rounded.ArrowForward, contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun DetailsItem4() {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(153.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(30.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "用户评价", color = Gray1, fontSize = 13.sp)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "(1680)", color = Gray3, fontSize = 10.sp)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "查看全部", color = Gray2, fontSize = 13.sp)
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = Icons.Rounded.ArrowForward, contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

        }
        Divider(
            modifier = Modifier
                .padding(10.dp)
                .height(0.3.dp), color = Color.Black
        )

        Row(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.avatar), contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(Modifier.fillMaxSize()) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "金金设计**铺",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "2021.12.27",
                        color = Gray2,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(Modifier.fillMaxWidth()) {
                    repeat(5) {
                        Icon(
                            painter = painterResource(id = R.drawable.level),
                            contentDescription = null,
                            modifier = Modifier.size(11.dp),
                            tint = if (it < 4) Yellow1 else Gray2
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "入手很多，活动力度很大必须囤起来，苹果很脆，水分足，甜度可口，没有酸感，大小很合适。",
                    color = Gray2,
                    fontSize = 12.sp,
                    maxLines = 3
                )
            }
        }
    }
}

@Composable
fun DetailsItem5() {
    Image(
        painter = painterResource(id = R.drawable.zhanshi), contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    )
}

@Composable
fun DetailsItem6() {
//    Row(
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth()
//            .height(205.dp)
//            .clip(RoundedCornerShape(10.dp))
//            .background(Color.White)
//            .padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically
//    ) {
//
//    }


}

@Composable
fun DetailsItem7() {
    Column(
        Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .fillMaxWidth()
            .height(205.dp)
            .padding(10.dp)
    ) {
        Text(text = "规格信息", fontSize = 13.sp, color = Gray1)
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        10.dp
                    )
                )
                .background(Gray5)
                .fillMaxWidth()
                .height(143.dp)
                .padding(1.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(34.5.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 10.dp))
                        .fillMaxHeight()
                        .width(142.dp)
                        .background(Color(0xffF8F9FA)), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "产地",
                        fontSize = 12.sp,
                        color = Gray2, textAlign = TextAlign.Center,
                    )
                }
                Spacer(modifier = Modifier.width(1.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 10.dp))
                        .fillMaxSize()
                        .background(Color.White), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "安徽",
                        fontSize = 12.sp,
                        color = Gray2, textAlign = TextAlign.Center,
                    )
                }

            }
            Spacer(modifier = Modifier.height(1.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(34.5.dp), verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(142.dp)
                        .background(Color(0xffF8F9FA)), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "规格",
                        fontSize = 12.sp,
                        color = Gray2, textAlign = TextAlign.Center,
                    )
                }
                Spacer(modifier = Modifier.width(1.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "180+/份",
                        fontSize = 12.sp,
                        color = Gray2, textAlign = TextAlign.Center,
                    )
                }

            }
            Spacer(modifier = Modifier.height(1.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(34.5.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(142.dp)
                        .background(Color(0xffF8F9FA)), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "保质期",
                        fontSize = 12.sp,
                        color = Gray2, textAlign = TextAlign.Center,
                    )
                }
                Spacer(modifier = Modifier.width(1.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "30天",
                        fontSize = 12.sp,
                        color = Gray2, textAlign = TextAlign.Center,
                    )
                }

            }
            Spacer(modifier = Modifier.height(1.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(34.5.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 10.dp))
                        .fillMaxHeight()
                        .width(142.dp)
                        .background(Color(0xffF8F9FA)), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "存储方式",
                        fontSize = 12.sp,
                        color = Gray2, textAlign = TextAlign.Center,
                    )
                }
                Spacer(modifier = Modifier.width(1.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 10.dp))

                        .fillMaxSize()
                        .background(Color.White), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "冷藏",
                        fontSize = 12.sp,
                        color = Gray2, textAlign = TextAlign.Center,
                    )
                }

            }

        }
    }
}

@Preview
@Composable
fun xx() {
    DetailsItem7()
}