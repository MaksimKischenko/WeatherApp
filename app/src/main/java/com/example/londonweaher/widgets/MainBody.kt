import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.londonweaher.R
import com.example.londonweaher.models.WeatherModel
import com.example.londonweaher.ui.theme.BlueLight
import com.example.londonweaher.ui.theme.TextDark
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState

import kotlinx.coroutines.launch


@Composable
fun HeaderElement(
    state: MutableState<WeatherModel>,
    onClickSync: ()-> Unit,
    onClickSearch: (city:String)-> Unit
) {
    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = BlueLight
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            ),
            shape = RoundedCornerShape(4.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = state.value.timeDate,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${state.value.icon}@2x.png",
                        contentDescription = "img2",
                        modifier = Modifier.size(35.dp)
                    )
                }
                Text(
                    text = state.value.city,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "${state.value.currentTemp}°C",
                    style = TextStyle(
                        fontSize = 84.sp,
                        color = TextDark
                    )
                )
                Text(
                    text = state.value.mainDesc,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.White
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                   IconButton(
                       onClick = {
                           onClickSearch.invoke("")
                       }

                   ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "svg1",
                        tint = TextDark

                    )
                  }
                    IconButton(
                        onClick = {
                            Log.d("MyLog", "MyLogMyLogMyLog")
                            onClickSync.invoke()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sync),
                            contentDescription = "svg2",
                            tint = TextDark
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabElement(state: MutableState<WeatherModel>) {
    val tabs = listOf("TEMPERATURE", "ADDITIONALLY")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .clip(RoundedCornerShape(2.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos),
                )
            },
            backgroundColor = BlueLight,
            contentColor = Color.White
        ) {
            tabs.forEachIndexed { index, text ->
                Tab(
                    modifier = Modifier.padding(4.dp),
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                ) {
                    Text(
                        text = text
                    )
                }
            }
        }
        HorizontalPager(
            count = tabs.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) {
            index -> Box(
            modifier = Modifier.fillMaxHeight()
            ) {
               if (index == 0) InfoItemTemperature(state)
               else InfoItemAdditionally(state)
          }
        }
    }
}

