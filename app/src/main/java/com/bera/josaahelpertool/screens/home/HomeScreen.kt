package com.bera.josaahelpertool.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bera.josaahelpertool.navigation.Routes
import com.bera.josaahelpertool.utils.CustomOutlinedCard
import com.bera.josaahelpertool.utils.ShimmerListItem
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    isDarkMode: Boolean,
    onDarkModeToggle: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ultimate JoSAA",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Start).padding(6.dp),
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colorScheme.primary
            )
            TopHalf(
                modifier = Modifier
                    .weight(10f)
                    .padding(6.dp),
                navController,
                viewModel.slideImage,
                viewModel::changeImagePage,
                isDarkMode,
                onDarkModeToggle
            )
            MainGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                navController,
                viewModel.drawableIds
            )
            QuoteBox(
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                viewModel.quote,
                viewModel.author,
                viewModel.isQuoteLoading
            )
            Text(
                text = "Made with ♥ by BERA",
                modifier = Modifier.weight(0.6f),
                textAlign = TextAlign.Center,
                fontSize = 11.sp,
                fontFamily = FontFamily.Serif
            )
        }

    }

}

@Composable
fun QuoteBox(modifier: Modifier, quote: String, author: String, isLoading: Boolean) {
    LazyColumn(modifier = modifier) {
        item {
            ElevatedCard(Modifier.padding(2.dp)) {
                ShimmerListItem(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    isLoading = isLoading,
                    barCount = 2
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Quote of the day",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Justify,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                        )
                        Text(
                            text = "' $quote '",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Justify,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                        )
                        Text(
                            text = "- $author",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Cursive
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopHalf(
    modifier: Modifier = Modifier,
    navController: NavController,
    slideImage: Array<Int>,
    changeImage: KSuspendFunction2<PagerState, Boolean, Unit>,
    isDarkMode: Boolean,
    onDarkModeToggle: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        4
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
    ) {
        HorizontalPager(state = pagerState, key = { slideImage[it] }) { index ->
            Image(
                painter = painterResource(id = slideImage[index]),
                contentDescription = "IIT Bombay",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        FilledTonalIconButton(
            onClick = { scope.launch { changeImage(pagerState, false) } },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .alpha(0.6f)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous Image",
                modifier = modifier.size(24.dp),
            )
        }

        FilledTonalIconButton(
            onClick = { scope.launch { changeImage(pagerState, true) } },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .alpha(0.6f),
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Next Image",
                modifier = modifier.size(24.dp),
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-40).dp),
            onClick = { navController.navigate(Routes.CBRScreen.route) }) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Search for colleges by Rank",
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace
            )
        }

//        Box(
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(10.dp)
//        ) {
//            ThemeSwitcher(
//                darkTheme = isDarkMode,
//                onClick = onDarkModeToggle,
//                size = 50.dp
//            )
//        }
    }
}

@Composable
fun MainGrid(modifier: Modifier, navController: NavController, drawableIds: Array<Int>) {

    val cardText = arrayOf(
        "IIT",
        "NIT",
        "IIIT",
        "GFTI",
    )

    val categoryArr = arrayOf("iit", "nit", "iiit", "ogc")

    CustomOutlinedCard(modifier = modifier, label = " All Cutoffs ") {
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(4, key = { categoryArr[it] }) {
                Button(
                    onClick = { navController.navigate(Routes.CollegeScreen.route + "/" + categoryArr[it]) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    shape = RoundedCornerShape(14.dp),
                    elevation = ButtonDefaults.buttonElevation(2.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2.5f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = drawableIds[it]),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            contentDescription = cardText[it],
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                        Text(
                            text = cardText[it],
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(2f)
                        )
                    }
                }
            }
        }
    }
}