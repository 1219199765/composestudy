package com.common.compose14.view.search

import HomeBean
import HomeViewRoot
import LogUtils
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.common.compose14.common.room.Search
import com.common.compose14.ui.theme.CommonBg
import com.common.compose14.ui.theme.Gray1
import com.common.compose14.ui.theme.Gray2
import com.common.compose14.ui.theme.Gray3
import com.common.compose14.ui.theme.Gray7

var SearchLocalSearchData = compositionLocalOf { mutableListOf<Search>() }

@Composable
fun SearchView(viewModel: SearchViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    viewModel.dispatch(SearchIntent.RequestAllRecords)

    val state = viewModel.state.collectAsStateWithLifecycle().value
    if (state is SearchState.RequestRecordsSus) {
        CompositionLocalProvider(SearchLocalSearchData provides state.data) {
            SearchViewRoot(deleAll = {
                viewModel.dispatch(SearchIntent.RequestDeleteAll)
            },
                onClick = {
                    viewModel.currentSearch = Search(it)
                    viewModel.dispatch(SearchIntent.RequestAddRecords)
                }
            )
        }
    }

}

@Composable
fun SearchViewRoot(onClick: (content: String) -> Unit, deleAll: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(CommonBg)
            .statusBarsPadding()
    ) {
        SearchSearch() {
            onClick.invoke(it)
        }

        SearchTitle() {
            deleAll.invoke()
        }
        SearchFlow()
        SearchTitle2()
        SearchFlow2()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchSearch(onClick: (content: String) -> Unit) {
    val keyboard = LocalSoftwareKeyboardController.current

    var text by remember { mutableStateOf("") }
    var hint = "生鲜美食"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .background(Color.White, CircleShape)
                .height(35.dp)
                .weight(1f)
                .padding(horizontal = 20.dp),
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    // 当空字符时, 显示hint
                    if (text.isEmpty())
                        Text(text = hint, color = Gray3, fontSize = 11.sp)

                    // 原本输入框的内容
                    innerTextField()
                }

            },
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onClick.invoke(text)
                    keyboard?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "取消", fontSize = 14.sp, color = Gray2)
    }
}

@Composable
fun SearchTitle(deleAll: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
        Text(text = "历史搜索", fontSize = 15.sp, color = Gray1)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Rounded.Delete,
            contentDescription = null,
            modifier = Modifier.clickable(
                onClick = {
                    deleAll.invoke()
                },
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                })
        )
    }
}

@Composable
fun SearchTitle2() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
        Text(text = "实时热搜", fontSize = 15.sp, color = Gray1)
        Spacer(modifier = Modifier.weight(1f))

    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchFlow() {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SearchLocalSearchData.current.forEach { search ->
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .background(Gray7)
                    .padding(horizontal = 12.dp, vertical = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = search.records.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        Gray2
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchFlow2() {
    val titles = mutableListOf<String>("车厘子","草莓","水果箱","精选红酒","火锅到家","新鲜大虾仁","冬季零食","速冻食品","酒水饮料","原神启动","白银段位","疾风剑豪")
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        titles.forEach { search ->
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .background(Gray7)
                    .padding(horizontal = 12.dp, vertical = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = search,
                    style = MaterialTheme.typography.labelMedium.copy(
                        Gray2
                    )
                )
            }
        }
    }
}


