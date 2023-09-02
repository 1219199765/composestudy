package com.common.compose14.view.car

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.common.compose14.R
import com.common.compose14.ui.theme.CommonBg
import com.common.compose14.ui.theme.Gray1
import com.common.compose14.ui.theme.Gray2
import com.common.compose14.ui.theme.Gray3
import com.common.compose14.ui.theme.Gray4
import com.common.compose14.ui.theme.Gray6
import com.common.compose14.ui.theme.Red1

@Composable
fun CarView() {
    CarViewRoot()
}

@Composable
fun CarViewRoot() {
    Column(
        Modifier
            .fillMaxSize()
            .background(CommonBg)
            .statusBarsPadding()
    ) {
        CarTopBar()
        CarList(modifier = Modifier.weight(1f))
        CarBottomBar()
    }
}

@Composable
fun CarTopBar() {
    Row(
        Modifier
            .background(White)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "购物车", style = TextStyle(fontSize = 18.sp, lineHeight = 25.sp))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "删除", style = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, color = Gray2))

    }
}

@Composable
fun CarList(modifier: Modifier = Modifier) {
    LazyColumn(modifier.fillMaxSize()) {
        items(5) {
            CarListItem()
        }
    }
}

@Composable
fun CarListItem() {
    var checkedState by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(124.dp)
            .background(
                White
            )
            .padding(10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.size(20.dp), checked = checkedState,
            onCheckedChange = {
                checkedState = it
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = Gray4
            ),
        )
        AsyncImage(
            model = R.drawable.avatar,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(horizontal = 10.dp)
        )
        Column(Modifier.fillMaxSize()) {
            Text(
                text = "鲜活 江苏洪泽湖南大闸蟹3对,四川眉山 爱媛38号果冻橙礼盒装",
                style = MaterialTheme.typography.labelLarge.copy(
                    Gray1
                )
            )

            Row(
                Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "特价", style = MaterialTheme.typography.labelSmall.copy(Red1),
                    modifier = Modifier
                        .border(
                            border = BorderStroke(width = 1.dp, color = Red1),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .padding(horizontal = 2.dp)
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "24H发货",
                    style = MaterialTheme.typography.labelSmall.copy(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .padding(horizontal = 2.dp)
                )
            }

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "39.9", style = MaterialTheme.typography.labelSmall.copy(Gray3),
                textDecoration = TextDecoration.LineThrough
            )

            Row {
                Text(
                    text = "￥", style = MaterialTheme.typography.labelSmall.copy(Red1),
                )
                Text(
                    text = "39.9",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        Red1,
                        fontWeight = FontWeight.Bold
                    ),
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(22.dp)
                        .background(Gray4), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Clear, contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                }
                Text(
                    text = "1", style = MaterialTheme.typography.bodySmall.copy(Gray1),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(22.dp)
                        .background(Gray4), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add, contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CarBottomBar() {
    var checkedState by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(White)
            .padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier
                .padding(end = 10.dp)
                .size(20.dp), checked = checkedState,
            onCheckedChange = {
                checkedState = it
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = Gray4
            ),
        )
        Text(text = "全选", style = MaterialTheme.typography.labelMedium.copy(Gray1))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "合计：  ￥", style = MaterialTheme.typography.labelMedium.copy(Gray1))
        Text(text = "79.8", style = MaterialTheme.typography.bodyMedium.copy(Gray1))
        Box(
            modifier = Modifier
                .padding(start = 15.dp)
                .clip(CircleShape)
                .width(89.dp)
                .height(35.dp)
                .background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center
        ){
            Text(text = "去结算", style = MaterialTheme.typography.labelLarge.copy(White))
        }
    }
}

