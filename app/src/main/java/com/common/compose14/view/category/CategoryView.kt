package com.common.compose14.view.category

import CommonLoading
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.biubiu.eventbus.observe.observeEvent
import com.common.compose14.R
import com.common.compose14.bean.CategoryBean
import com.common.compose14.bean.CategoryContentBean
import com.common.compose14.ui.theme.CommonBg
import com.common.compose14.ui.theme.Gray1
import com.common.compose14.ui.theme.Gray2
import com.common.compose14.ui.theme.Gray3
import com.common.compose14.ui.theme.Gray4
import com.common.compose14.ui.theme.Red1
import com.common.compose14.view.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

var LocalData = compositionLocalOf { CategoryBean() }
var LocalContentData = compositionLocalOf { CategoryContentBean() }
var CategoryLocalViewModel = compositionLocalOf { CategoryViewModel() }

@Composable
fun CategoryView(viewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {




    val scope = rememberCoroutineScope()
    initData(viewModel, scope = scope)

    val state = viewModel.state.collectAsState().value
    val state2 = viewModel.state2.collectAsState().value

    if (state is CategoryState.RequestCategorySus && state2 is CategoryState.RequestCategoryContentSus) {
        CompositionLocalProvider(
            LocalData provides state.data,
            CategoryLocalViewModel provides viewModel,
            LocalContentData provides state2.data
        ) {
            state.data.data?.let {
                CategoryRoot()
            }
        }

    } else {
        CommonLoading()
    }


}

@Composable
fun CategoryRoot() {
    Column(
        Modifier
            .fillMaxSize()
            .background(CommonBg)
            .statusBarsPadding()
    ) {
        CategorySearch()
        Row(Modifier.fillMaxSize()) {
            CategoryLeftList()
            Column(Modifier.fillMaxSize()) {
                CategoryTopList()
                CagetoryContentList()
            }
        }
    }
}

@Composable
fun CategorySearch() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(Gray4, CircleShape)
                .height(36.dp)
                .weight(1f)
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "吃出美好生活",
                    style = MaterialTheme.typography.bodySmall.copy(color = Gray3)
                )
            }
        }
        Spacer(modifier = Modifier.width(14.dp))
        Icon(
            painter = painterResource(id = R.drawable.car), contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }

}

@Composable
fun CategoryLeftList() {
    val data = LocalData.current.data

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .width(90.dp)
    ) {
        items(data!!.size) {
            CategoryLeftListItem(data[it], it)
        }
    }
}

@Composable
fun CategoryLeftListItem(itemData: CategoryBean.Data, index: Int) {
    val viewModel = CategoryLocalViewModel.current
    Box(
        modifier = Modifier
            .width(90.dp)
            .height(55.dp)
            .background(if (index == viewModel.leftIndex) White else CommonBg)
            .clickable(
                onClick = {
                    viewModel.leftIndex = index
                    viewModel.topIndex = 0
                    viewModel.dispatch(CategoryIntent.RequestCategoryContent)
                },
                // 去除点击效果
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = itemData.name,
            style = MaterialTheme.typography.bodySmall.copy(if (index == viewModel.leftIndex) MaterialTheme.colorScheme.primary else Gray2)
        )
    }
}

@Composable
fun CategoryTopList() {
    val data = LocalData.current.data?.get(CategoryLocalViewModel.current.leftIndex)?.children
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .height(55.dp)
            .padding(end = 10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        items(data!!.size) {
            CategoryTopListItem(data[it], it)
        }
    }
}

@Composable
fun CategoryTopListItem(itemData: CategoryBean.Children, index: Int) {
    val viewModel = CategoryLocalViewModel.current
    Box(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(if (index == viewModel.topIndex) MaterialTheme.colorScheme.primary else Gray4)
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .clickable(
                onClick = {
                    viewModel.topIndex = index
                    viewModel.dispatch(CategoryIntent.RequestCategoryContent)
                },
                // 去除点击效果
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                })
    ) {
        Text(
            text = itemData.name,
            style = MaterialTheme.typography.labelMedium.copy(if (index == viewModel.topIndex) Gray1 else Gray3)
        )
    }
}


@Composable
fun CagetoryContentList() {
    val data = LocalContentData.current.data
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(data!!.records) {
            CagetoryContentListItem(it)
        }
    }
}

@Composable
fun CagetoryContentListItem(itemData: CategoryContentBean.Record) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .height(110.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(White)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = itemData.coverPicUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Gray4)
                .size(90.dp)
        )
        Column(Modifier.fillMaxSize()) {
            Text(
                text = itemData.goodsName,
                style = MaterialTheme.typography.labelLarge.copy(
                    Gray1,
                    fontWeight = FontWeight.Bold
                ), maxLines = 1, overflow = TextOverflow.Clip
            )

            Text(
                text = itemData.spec,
                style = MaterialTheme.typography.labelMedium.copy(
                    Gray3,
                    fontWeight = FontWeight.Normal
                ), maxLines = 1, overflow = TextOverflow.Clip, modifier = Modifier.padding(top = 2.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row {
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
                    text = "24H发货", style = MaterialTheme.typography.labelSmall.copy(MaterialTheme.colorScheme.primary),
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

            Row(modifier = Modifier

                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "￥", style = MaterialTheme.typography.labelSmall.copy(Red1),
                    )

                Text(
                    text = ((itemData.price)/100).toString(), style = MaterialTheme.typography.bodyMedium.copy(Red1, fontWeight = FontWeight.Bold),

                    )
                Text(
                    text = "/${itemData.units}", style = MaterialTheme.typography.labelSmall.copy(Gray3),

                    )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "￥${(itemData.price)/100}/${itemData.units}", style = MaterialTheme.typography.labelSmall.copy(Gray3),
                        textDecoration = TextDecoration.LineThrough
                    )
                Spacer(modifier = Modifier.weight(1f))

                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.car),
                        contentDescription = null,
                        modifier = Modifier.size(15.dp),
                        tint = Color.White
                    )
                }

            }
        }
    }
}

fun initData(viewModel: CategoryViewModel, scope: CoroutineScope) {
    if (viewModel.repeatRequest) {
        viewModel.repeatRequest = false
        scope.launch {
            viewModel.dispatch(CategoryIntent.RequestCategory)
        }
    }

}

