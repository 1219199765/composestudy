package com.common.compose14.view.search

import android.widget.Space
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.common.compose14.ui.theme.CommonBg
import com.common.compose14.ui.theme.Gray2
import com.common.compose14.ui.theme.Gray3

@Composable
fun SearchView() {
    SearchViewRoot()
}

@Composable
fun SearchViewRoot() {
    Column(Modifier.fillMaxSize().background(CommonBg).statusBarsPadding()) {
        SearchSearch()
    }
}

@Composable
fun SearchSearch() {
    var text by remember { mutableStateOf("") }
    var hint = "生鲜美食"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = 10.dp)
            ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = null,
            modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(10.dp))
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .background(Color.White, CircleShape)
                .height(35.dp)
                .weight(1f).padding(horizontal = 20.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart){
                    // 当空字符时, 显示hint
                    if(text.isEmpty())
                        Text(text = hint, color = Gray3, fontSize = 11.sp)

                    // 原本输入框的内容
                    innerTextField()
                }

            }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "取消", fontSize = 14.sp, color = Gray2)
    }
}

@Preview
@Composable
fun xx() {
    SearchSearch()
}