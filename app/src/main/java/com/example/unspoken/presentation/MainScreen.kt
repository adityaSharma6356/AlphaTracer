package com.example.unspoken.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.unspoken.R
import com.example.unspoken.ui.theme.mainFont
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
//    viewModel: MainViewModel = hiltViewModel()
) {
    val mainNavController = rememberNavController()
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground),
        navController = mainNavController,
        startDestination = "start"
    ) {
        composable("start") {
            val drawerState = rememberPagerState(pageCount = { 2 }, initialPage = 1, initialPageOffsetFraction = 0.2f)
            val offset by remember {
                derivedStateOf {
                    drawerState.getOffsetFractionForPage(1).absoluteValue
                }
            }
            val scope = rememberCoroutineScope()
            HorizontalPager(
                pageSize = PageSize.Fill,
                state = drawerState,
                beyondBoundsPageCount = 2,
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier.offset(x = -(100*offset).dp)
                ) {
                    if (it == 0) {
                        Column(
                            modifier = Modifier
                                .padding(start = 100.dp)
                                .fillMaxSize()
                                .alpha(offset.pow(4)),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Spacer(modifier = Modifier.height(50.dp))
                            Column(modifier = Modifier.padding(start = 20.dp).background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f), RoundedCornerShape(20.dp))) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "account",
                                        modifier = Modifier
                                            .padding(start = 10.dp)
                                            .size(70.dp),
                                        tint = MaterialTheme.colorScheme.surface
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(text = "Ben Dover", fontSize = 25.sp, color = MaterialTheme.colorScheme.background, fontFamily = mainFont, modifier = Modifier)
                                        Text(text = "bendover@adgitmdelhi.ac.in", fontSize = 15.sp, color = MaterialTheme.colorScheme.background.copy(alpha = 0.6f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    }
                                }
                                Spacer(
                                    modifier = Modifier
                                        .padding(0.dp, 20.dp, 0.dp, 20.dp)
                                        .height(2.dp)
                                        .background(MaterialTheme.colorScheme.onBackground)
                                        .fillMaxWidth()
                                )

                                SideNavMenuText(text = "My Posts", icon = {
                                    Icon(imageVector = Icons.Default.Analytics, contentDescription = "", modifier = Modifier.size(30.dp), tint = MaterialTheme.colorScheme.surface)
                                })
                                SideNavMenuText(text = "Tips", icon = {
                                    Icon(painter = painterResource(id = R.drawable.idea), contentDescription = "", modifier = Modifier.size(30.dp), tint = MaterialTheme.colorScheme.surface)
                                })
                                SideNavMenuText(text = "Alumni", icon = {
                                    Icon(painter = painterResource(id = R.drawable.student), contentDescription = "", modifier = Modifier.size(30.dp), tint = MaterialTheme.colorScheme.surface)
                                })
                                SideNavMenuText(text = "Placements", icon = {
                                    Icon(painter = painterResource(id = R.drawable.placement), contentDescription = "", modifier = Modifier.size(30.dp), tint = MaterialTheme.colorScheme.surface)
                                })
                                SideNavMenuText(text = "Colleges", icon = {
                                    Icon(painter = painterResource(id = R.drawable.college), contentDescription = "", modifier = Modifier.size(30.dp), tint = MaterialTheme.colorScheme.surface)
                                })
                                SideNavMenuText(text = "Settings", icon = {
                                    Icon(imageVector = Icons.Default.Settings, contentDescription = "", modifier = Modifier.size(30.dp), tint = MaterialTheme.colorScheme.surface)
                                })
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onBackground),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .scale((1f - 0.1f * offset).coerceIn(0.8f, 1f))
                                    .clip(RoundedCornerShape((50 * offset).dp))
                                    .background(MaterialTheme.colorScheme.surface)
                            ) {
                                Spacer(modifier = Modifier.height((30 * (1 - offset)).dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(top = 20.dp, start = 25.dp)
                                        .fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                scope.launch { if (offset > 0.5f) drawerState.animateScrollToPage(1) else drawerState.animateScrollToPage(0) }
                                            }
                                    ) {
                                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(if (isSystemInDarkTheme()) R.raw.light_arrow else R.raw.arrow_light))
                                        LottieAnimation(
                                            composition = composition,
                                            progress = { offset / 2 },
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(25.dp))
                                    Text(text = stringResource(id = R.string.app_name), fontSize = 25.sp, fontFamily = mainFont, color = MaterialTheme.colorScheme.onBackground)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "account",
                                        modifier = Modifier
                                            .padding(end = 20.dp)
                                            .size(40.dp),
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SideNavMenuText(text: String, fontSize: TextUnit = 25.sp, onCLick: () -> Unit = {}, icon: @Composable () -> Unit = {}) {
    Box(
        modifier = Modifier
            .padding(start = 10.dp, bottom = 5.dp, end = 10.dp)
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .clickable { onCLick() }
            .padding(10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon()
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = text, fontSize = fontSize, color = MaterialTheme.colorScheme.background, fontFamily = mainFont, modifier = Modifier)
        }
    }
}
