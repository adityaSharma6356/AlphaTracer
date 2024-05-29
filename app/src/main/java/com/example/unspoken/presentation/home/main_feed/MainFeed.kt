package com.example.unspoken.presentation.home.main_feed

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unspoken.R
import com.example.unspoken.domain.MainFeedItem
import com.example.unspoken.ui.theme.mainFont
import kotlinx.coroutines.launch

@Composable
fun MainFeedUi(
    viewModel: MainFeedViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val lazyState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {
                        viewModel.selectTrending(true)
                        if (uiState.selectedTrending) {
                            scope.launch {
                                lazyState.animateScrollToItem(0)
                            }
                        }
                    }
                    .background(if (uiState.selectedTrending) MaterialTheme.colorScheme.tertiary else Color.Transparent)
            ) {
               Text(text = "Trending", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(0.dp, 5.dp), fontFamily = mainFont)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { viewModel.selectTrending(false) }
                    .background(if (!uiState.selectedTrending) MaterialTheme.colorScheme.tertiary else Color.Transparent)
            ) {
                Text(text = "My College", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(0.dp, 5.dp), fontFamily = mainFont)
            }
        }
        Crossfade(
            targetState = uiState.selectedTrending,
            label = "f",
            modifier = Modifier.fillMaxSize()
        ) {
            if(it){
                TrendingFeedUi(state = lazyState ,feedList = uiState.mainFeed, status = uiState.statusList)
            } else {
                MyCollegeScreen()
            }
        }

    }
}

@Composable
fun MyCollegeScreen(
){

}

@Composable
fun TrendingFeedUi(
    state: LazyListState,
    feedList: List<MainFeedItem>,
    status: List<Status>
){
    Column(modifier = Modifier) {
        LazyColumn(state = state, contentPadding = PaddingValues(top = 5.dp, bottom = 100.dp)) {
            item {
                Row(
                    modifier = Modifier
                        .padding(5.dp, 20.dp, 5.dp, 5.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(15.dp))
                    SingleStatus(status = Status(-1, "Add status", "Add Status", R.drawable.add_icon, true))
                    status.forEach {
                        SingleStatus(status = it)
                    }
                }
            }
            items(feedList.size){
                when(feedList[it]){
                    is MainFeedItem.SimpleFeed -> {
                        SimpleFeedUi(data = feedList[it] as MainFeedItem.SimpleFeed)
                        Spacer(modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)))
                    }
                }
            }
        }
    }
}


@Composable
fun SingleStatus(
    status: Status
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(5.dp, 0.dp)
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
                .then(
                    if (status.seen) {
                        Modifier
                    } else {
                        Modifier
                            .border(3.dp, Color.Green, CircleShape)
                            .padding(5.dp)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (status.id==-1){
                Icon(
                    painter = painterResource(id = status.userImage),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(30.dp),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }else{
                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = status.userImage),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(70.dp),
                    contentDescription = null
                )
            }
        }
        Text(
            text = status.userName,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Visible,
            maxLines = 1,
            modifier = Modifier.then(if(status.id==-1) Modifier else Modifier.width(70.dp)),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SimpleFeedUi(
    data: MainFeedItem.SimpleFeed
){
    Column(
        modifier = Modifier
            .padding(5.dp, 5.dp, 5.dp, 0.dp)
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .padding(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(data.authorProfilePhoto), contentDescription = "", modifier = Modifier
                .size(45.dp)
                .clip(CircleShape))
            Spacer(modifier = Modifier.width(10.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = "@"+data.authorId, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
                Text(text = data.collegeId, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = data.date, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f), modifier = Modifier.align(Alignment.Top))
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(50))
                .width(70.dp)
                .height(30.dp)) {
                Text(text = "follow", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = data.title, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.ExtraBold, maxLines = 2)
        Text(text = data.content, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f), maxLines = 2, overflow = TextOverflow.Ellipsis)
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BottomIcons(id = R.drawable.heart, value = data.likes, color = Color(255, 72, 133))
            BottomIcons(id = R.drawable.chat, value = data.comments, color = MaterialTheme.colorScheme.onBackground)
            BottomIcons(id = R.drawable.eye, value = data.views, color = Color(0, 102, 200))
        }
    }
}

@Composable
fun BottomIcons(id: Int, value: String, color: Color){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(modifier = Modifier.size(20.dp) ,painter = painterResource(id = id), contentDescription = null, tint = color)
        Spacer(modifier = Modifier.width(5.dp))
        if (value.isNotBlank()){
            Text(text = value, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground)
        }

    }
}