package com.common.compose14.view

import HomeView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.common.compose14.R
import com.common.compose14.ui.theme.Gray2
import com.common.compose14.view.car.CarView
import com.common.compose14.view.category.CategoryView

var LocalMainViewModel = compositionLocalOf { MainViewModel() }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainView(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

//    val systemUiController = rememberSystemUiController()
//    systemUiController.setSystemBarsColor(Color.White, false)
//    systemUiController.setStatusBarColor(Color.Transparent, true)



    val pagerState = rememberPagerState(pageCount = {
        4
    })


    LaunchedEffect(key1 = viewModel.currentPage){
        pagerState.scrollToPage(page = viewModel.currentPage, pageOffsetFraction = 0f)
    }

    CompositionLocalProvider( LocalMainViewModel provides viewModel) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Content(
                pagerState = pagerState, modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            BottomNav(pagerState)
        }
    }



}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content(pagerState: PagerState, modifier: Modifier = Modifier) {
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        modifier = modifier,
        beyondBoundsPageCount = 4
    ) { page ->
        when (page) {
            0 -> {
                HomeView()
            }

            1 -> {
                CategoryView()
            }


            2 -> {
                CarView()
            }

            3 -> {
                UserView()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNav(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val viewModel = LocalMainViewModel.current
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    onClick = {viewModel.currentPage = 0  },
                    // 去除点击效果
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Icon(
                painter = painterResource(id = R.drawable.home),
                contentDescription = null,
                tint = if (0 == pagerState.currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp),

                )
            Text(
                text = "首页",
                style = MaterialTheme.typography.labelMedium,
                color = if (0 == pagerState.currentPage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    onClick = { viewModel.currentPage = 1 },
                    // 去除点击效果
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Icon(
                painter = painterResource(id = R.drawable.category),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (1 == pagerState.currentPage) MaterialTheme.colorScheme.primary else Gray2,
            )
            Text(
                text = "淘碟",
                style = MaterialTheme.typography.labelMedium,
                color = if (1 == pagerState.currentPage) MaterialTheme.colorScheme.primary else Gray2
            )
        }


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    onClick = { viewModel.currentPage = 2 },
                    // 去除点击效果
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Icon(
                painter = painterResource(id = R.drawable.car),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (2 == pagerState.currentPage) MaterialTheme.colorScheme.primary else Gray2,
            )
            Text(
                text = "动态",
                style = MaterialTheme.typography.labelMedium,
                color = if (2 == pagerState.currentPage) MaterialTheme.colorScheme.primary else Gray2
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .clickable(
                    onClick = { viewModel.currentPage = 3 },
                    // 去除点击效果
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.user),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (3 == pagerState.currentPage) MaterialTheme.colorScheme.primary else Gray2,
            )
            Text(
                text = "我的",
                style = MaterialTheme.typography.labelMedium,
                color = if (3 == pagerState.currentPage) MaterialTheme.colorScheme.primary else Gray2
            )
        }
    }
}

