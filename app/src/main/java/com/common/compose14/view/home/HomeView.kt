import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import com.biubiu.eventbus.post.postEvent
import com.common.compose14.MoveIntent
import com.common.compose14.R
import com.common.compose14.bean.HomeBean
import com.common.compose14.common.event.AppMoveEvent
import com.common.compose14.common.event.AppScopeEvent
import com.common.compose14.ui.theme.CommonBg
import com.common.compose14.ui.theme.Gray1
import com.common.compose14.ui.theme.Gray2
import com.common.compose14.ui.theme.Gray3
import com.common.compose14.ui.theme.Gray4
import com.common.compose14.ui.theme.Gray5
import com.common.compose14.ui.theme.Red1
import com.common.compose14.ui.theme.Yellow1
import com.common.compose14.view.home.HomeIntent
import com.common.compose14.view.home.HomeRepository
import com.common.compose14.view.home.HomeState
import com.common.compose14.view.home.HomeViewModel
import com.loren.component.view.composesmartrefresh.SmartSwipeRefresh
import com.loren.component.view.composesmartrefresh.SmartSwipeRefreshState
import com.loren.component.view.composesmartrefresh.SmartSwipeStateFlag
import com.loren.component.view.composesmartrefresh.rememberSmartSwipeRefreshState
import com.zj.banner.BannerPager
import com.zj.banner.model.BaseBannerBean
import com.zj.banner.ui.config.BannerConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


var LocalData = compositionLocalOf { HomeBean() }
var LocalViewModel = compositionLocalOf { HomeViewModel() }


@Composable
fun HomeView(viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {


    val scope = rememberCoroutineScope()
    initData(viewModel, scope)

    val state = viewModel.state.collectAsState().value
    val refreshState = rememberSmartSwipeRefreshState()


    if (state is HomeState.RequestHomeSus) {
        CompositionLocalProvider(LocalData provides state.data, LocalViewModel provides viewModel) {
            state.data.data?.let {
                HomeViewRoot(refreshState = refreshState) {
                    viewModel.categoryIndex = it
                }
            }
        }
    } else {
        CommonLoading()
    }


}


@Composable
fun HomeViewRoot(refreshState: SmartSwipeRefreshState, onClick: (index: Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CommonBg)
    ) {
        HomeTopBar()
        HomeRefresh(refreshState = refreshState) {
            onClick.invoke(it)
        }
    }
}

@Composable
fun HomeTopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .padding(15.dp)

    ) {
        Icon(
            imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = "海淀区中关村大厦",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.sys),
            contentDescription = null,
            tint = Color.White, modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.msg),
            contentDescription = null,
            tint = Color.White, modifier = Modifier.size(24.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeRefresh(refreshState: SmartSwipeRefreshState, onClick: (index: Int) -> Unit) {

    SmartSwipeRefresh(state = refreshState,
        onRefresh = {
            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                refreshState.refreshFlag = SmartSwipeStateFlag.SUCCESS
            }
        },
        onLoadMore = {
            CoroutineScope(Dispatchers.IO).launch {

                delay(2000)
                refreshState.loadMoreFlag = SmartSwipeStateFlag.SUCCESS
            }
        },
        isNeedLoadMore = true,
        isNeedRefresh = true,
        headerIndicator = {
            MyRefreshHeader2(flag = refreshState.refreshFlag, true)
        },
        footerIndicator = {
            MyRefreshHeader2(flag = refreshState.loadMoreFlag, true)
        }) {
        CompositionLocalProvider(LocalOverscrollConfiguration.provides(null)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item { HomeSearch() }
                item { HomeBanenr() }
                item { HomeHorGrids() }
                item { HomeHuaSuan() }
                item { HomeHaoGood() }
                stickyHeader {
                    HomeCategory() {
                        onClick.invoke(it)
                    }
                }
                item {
                    HomeList()
                }
            }
        }
    }
}


@Composable
fun HomeSearch() {
    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 12.dp)
            .background(Gray4, CircleShape)
            .height(40.dp)
            .fillMaxWidth()
            .clickable(
                onClick = { postEvent(AppScopeEvent(0)) },
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                })
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
}

@Composable
fun HomeBanenr() {
    val items = mutableListOf<BannerBean>()
    for (item in LocalData.current.data?.banner!!) {
        items.add(BannerBean(item.imageUrl))
    }

    BannerPager(
        items = items,
        config = BannerConfig(imageRatio = 2.3f)
    ) { item ->
        Log.e("main", "--->:$item")
    }
}

@Composable
fun HomeHorGrids() {
    val grids = LocalData.current.data!!.grids
    LazyRow(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(80.dp)
    ) {
        items(grids.size) {
            GridsItem(itemData = grids[it], it)
        }
    }
}

@Composable
fun GridsItem(itemData: HomeBean.Grid, index: Int) {
    Column(
        Modifier
            .padding(horizontal = 5.dp)
            .fillMaxHeight()
            .width(55.dp)
            .clickable {
                postEvent(AppMoveEvent(index))
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = itemData.imageUrl, contentDescription = null, contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemData.content, style = MaterialTheme.typography.labelMedium.copy(Gray2))
    }
}

@Composable
fun HomeHuaSuan() {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .height(197.dp)
            .fillMaxWidth()
            .padding(15.dp)
    ) {

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "超划算",
                style = MaterialTheme.typography.bodyMedium.copy(
                    Gray1,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.width(7.dp))
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .background(Yellow1)
                    .padding(horizontal = 2.dp)
            ) {
                Text(
                    text = "冬日礼遇",
                    style = MaterialTheme.typography.labelSmall.copy(Color.White)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "查看全部",
                style = MaterialTheme.typography.labelMedium.copy(MaterialTheme.colorScheme.primary)
            )
        }

        Row(
            Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
        ) {
            HomeHuaSuanItem(R.drawable.img2, "四川爱媛38号", "39.9")
            HomeHuaSuanItem(R.drawable.img3, "山羊黄牛腱子肉", "39.9")
            HomeHuaSuanItem(R.drawable.img1, "有机水果卷心菜", "39.9")
        }

    }
}

@Composable
fun HomeHuaSuanItem(img: Int, des: String, price: String) {
    Column(
        modifier = Modifier
            .height(141.dp)
            .width(92.dp), horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            model = img, contentDescription = null,
            modifier = Modifier.size(92.dp), contentScale = ContentScale.Crop
        )
        Text(
            text = "四川爱媛38号",
            style = MaterialTheme.typography.labelMedium.copy(
                Gray2
            ),
            maxLines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier.padding(top = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "￥ 39.9", style = MaterialTheme.typography.bodyLarge.copy(Red1))
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
                    modifier = Modifier.size(13.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun HomeHaoGood() {
    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .height(128.dp)
                .padding(start = 15.dp, end = 15.dp, top = 10.dp)
        ) {
            Text(
                text = "好吃点",
                style = MaterialTheme.typography.bodyMedium.copy(
                    Gray1,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "美国小木瓜尝鲜", style = MaterialTheme.typography.labelMedium.copy(Gray3),
                modifier = Modifier.padding(top = 2.dp)
            )
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = R.drawable.img4,
                    contentDescription = null,
                    modifier = Modifier.size(65.dp)
                )
                AsyncImage(
                    model = R.drawable.img5,
                    contentDescription = null,
                    modifier = Modifier.size(65.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .height(128.dp)
                .padding(start = 15.dp, end = 15.dp, top = 10.dp)
        ) {
            Text(
                text = "产地量贩",
                style = MaterialTheme.typography.bodyMedium.copy(
                    Gray1,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "核桃19.9/一箱", style = MaterialTheme.typography.labelMedium.copy(Gray3),
                modifier = Modifier.padding(top = 2.dp)
            )
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = R.drawable.img6,
                    contentDescription = null,
                    modifier = Modifier.size(65.dp)
                )
                AsyncImage(
                    model = R.drawable.img7,
                    contentDescription = null,
                    modifier = Modifier.size(65.dp)
                )
            }
        }
    }

}

@Composable
fun HomeCategory(onClick: (index: Int) -> Unit) {
    Row(
        Modifier
            .background(Color.White)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(41.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .clickable(
                    onClick = { onClick.invoke(0) },
                    // 去除点击效果
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "全部",
                style = MaterialTheme.typography.bodyMedium.copy(if (LocalViewModel.current.categoryIndex == 0) MaterialTheme.colorScheme.primary else Gray1)
            )
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(CircleShape)
                    .background(if (LocalViewModel.current.categoryIndex == 0) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .padding(horizontal = 6.dp)
            ) {
                Text(
                    text = "猜你喜欢",
                    style = MaterialTheme.typography.labelMedium.copy(if (LocalViewModel.current.categoryIndex == 0) Color.White else Gray3),
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp), color = Gray5
        )
        Column(
            Modifier
                .fillMaxHeight()
                .clickable(
                    onClick = { onClick.invoke(1) },
                    // 去除点击效果
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "时令",
                style = MaterialTheme.typography.bodyMedium.copy(if (LocalViewModel.current.categoryIndex == 1) MaterialTheme.colorScheme.primary else Gray1)
            )
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(CircleShape)
                    .background(if (LocalViewModel.current.categoryIndex == 1) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .padding(horizontal = 6.dp)
            ) {
                Text(
                    text = "当季精选",
                    style = MaterialTheme.typography.labelMedium.copy(if (LocalViewModel.current.categoryIndex == 1) Color.White else Gray3),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp), color = Gray5
        )
        Column(
            Modifier
                .fillMaxHeight()
                .clickable(
                    onClick = { onClick.invoke(2) },
                    // 去除点击效果
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "进口",
                style = MaterialTheme.typography.bodyMedium.copy(if (LocalViewModel.current.categoryIndex == 2) MaterialTheme.colorScheme.primary else Gray1)
            )
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(CircleShape)
                    .background(if (LocalViewModel.current.categoryIndex == 2) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .padding(horizontal = 6.dp)
            ) {
                Text(
                    text = "国际直采",
                    style = MaterialTheme.typography.labelMedium.copy(if (LocalViewModel.current.categoryIndex == 2) Color.White else Gray3),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp), color = Gray5
        )
        Column(
            Modifier
                .fillMaxHeight()
                .clickable(
                    onClick = { onClick.invoke(3) },
                    // 去除点击效果
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "人气",
                style = MaterialTheme.typography.bodyMedium.copy(if (LocalViewModel.current.categoryIndex == 3) MaterialTheme.colorScheme.primary else Gray1)
            )
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(CircleShape)
                    .background(if (LocalViewModel.current.categoryIndex == 3) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .padding(horizontal = 6.dp)
            ) {
                Text(
                    text = "大家在买",
                    style = MaterialTheme.typography.labelMedium.copy(if (LocalViewModel.current.categoryIndex == 3) Color.White else Gray3),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

@Composable
fun HomeList() {
    val categoryIndex = LocalViewModel.current.categoryIndex

    val data = when (categoryIndex) {
        0 -> {
            LocalData.current.data!!.meatLsit
        }

        1 -> {
            LocalData.current.data!!.vegetableList
        }

        2 -> {
            LocalData.current.data!!.meatLsit
        }

        3 -> {
            LocalData.current.data!!.vegetableList
        }

        else -> {
            null
        }

    }

    val intNum = kotlin.math.ceil((data!!.size.toDouble() / 2).toDouble())

    val height = intNum * 230 + 10 * (intNum + 1)
    LogUtils.d("--->高度问题$height")


    // 纵向，横向的对应 Horizontal...
    LazyVerticalStaggeredGrid(
        // columns 参数类似于 LazyVerticalGrid
        columns = StaggeredGridCells.Fixed(2),
        // 整体内边距
        contentPadding = PaddingValues(10.dp, 10.dp),
        // item 和 item 之间的纵向间距
        verticalItemSpacing = 10.dp,
        // item 和 item 之间的横向间距
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.height(height.dp),
        userScrollEnabled = false
    ) {
        data!!.forEachIndexed { index, result ->
            item {
                HomeListItem(itemData = result)
            }
        }
    }

}

@Composable
fun HomeListItem(itemData: HomeBean.Vegetable) {
    Column(
        Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                Color.White
            )
            .clickable {
                postEvent(AppScopeEvent(itemData.goodsId))
            }
    ) {
        AsyncImage(
            model = itemData.imageUrl, contentDescription = null, contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(137.dp)
                .fillMaxWidth()
        )
        Text(
            text = itemData.goodsName,
            style = MaterialTheme.typography.bodyMedium.copy(Gray1, fontWeight = FontWeight.Bold),
            maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(), textAlign = TextAlign.Start
        )
        Row(
            Modifier
                .padding(10.dp)
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

        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "￥", style = MaterialTheme.typography.labelSmall.copy(Red1),

                )

            Text(
                text = ((itemData.goodsPrice) / 100).toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    Red1,
                    fontWeight = FontWeight.Bold
                ),

                )
            Text(
                text = "/箱", style = MaterialTheme.typography.labelSmall.copy(Gray3),

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


data class BannerBean(
    override val data: Any? = null
) : BaseBannerBean()


fun initData(viewModel: HomeViewModel, scope: CoroutineScope) {
    if (viewModel.repeatRequest) {
        scope.launch {
            viewModel.dispatch(HomeIntent.ReqestHome)
            viewModel.repeatRequest = false
        }
    }
}
