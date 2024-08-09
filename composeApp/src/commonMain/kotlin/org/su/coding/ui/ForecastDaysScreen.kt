package ui

import ForecastDaysViewModel
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.WeatherData
import domain.WeatherType
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.su.coding.util.getBackgroundColor
import org.su.coding.util.koinViewModel
import ui.theme.AppBackground
import weatherapp.composeapp.generated.resources.Res
import weatherapp.composeapp.generated.resources.baselin_navigate_next_24
import weatherapp.composeapp.generated.resources.baseline_location_on

const val maxBoxSize = 140f
const val minBoxSize = 52f

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun ForecastDaysScreen(onShowDetail: (WeatherData, Int) -> Unit) {

    val viewModel = koinViewModel<ForecastDaysViewModel>()

    val weatherDatas = viewModel.state.weatherDatas

    var currentTopbarBoxSize by remember {
        mutableStateOf(maxBoxSize)
    }

    val connection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y.toInt()
                val newImageSize = currentTopbarBoxSize + delta
                val perviousSize = currentTopbarBoxSize
                currentTopbarBoxSize = newImageSize.coerceIn(minBoxSize, maxBoxSize)
                val consumed = currentTopbarBoxSize - perviousSize

                return Offset(0f, consumed)
            }
        }
    }
    Box(
        Modifier.nestedScroll(connection)
            .fillMaxSize().background(AppBackground)
    ) {
        TopBar(
            Modifier
                .fillMaxWidth()
                .height(currentTopbarBoxSize.dp)
        )

        Box(
            Modifier.fillMaxSize()
                .padding(top = currentTopbarBoxSize.dp, start = 16.dp, end = 16.dp)
        ) {
            LazyColumn(contentPadding = PaddingValues(vertical = 10.dp)) {
                items(weatherDatas.size) { index ->
                    WeatherCard(
                        weatherDatas[index],
                        index,
                        onShowDetail = { weatherData, colorCode ->
                            onShowDetail(weatherData, colorCode)
                        },
                        Modifier
                            .animateItemPlacement(animationSpec = tween(300))
                            .padding(bottom = 10.dp)
                    )

                }
            }
        }

        if (viewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black
            )
        }

        viewModel.state.error?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}

@Composable
private fun TopBar(modifier: Modifier = Modifier) {
    Box(modifier) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                vectorResource(Res.drawable.baseline_location_on),
                contentDescription = "location",
                tint = Color.Black,
                modifier = Modifier.size(26.dp)
            )
            Text(
                "Yangon",
                fontSize = 24.sp,
                color = Color.Black
            )
        }
        Text(
            "This week",
            fontSize = 42.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
                .background(AppBackground)
                .padding(start = 32.dp, bottom = 10.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Preview
@Composable
fun WeatherCard(
    weatherData: WeatherData,
    colorCode: Int,
    onShowDetail: (WeatherData, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = getBackgroundColor(colorCode)
    Column(
        modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(colors.first, colors.second)
                ),
                shape = RoundedCornerShape(30.dp)
            )
            .padding(
                horizontal = 30.dp,
                vertical = 20.dp
            )
    ) {
        val weatherType = WeatherType.fromWMO(weatherData.weatherCode)

        Box(
            Modifier
                .background(colors.second, RoundedCornerShape(20.dp))
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            if (weatherData.date.isNotEmpty()) {
                Text(
                    weatherData.date,
                    color = Color.White
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "${weatherData.temperatureCelsius}°",
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.width(20.dp))

            Column() {
                Text(
                    weatherType.weatherDesc,
                    fontSize = 22.sp,
                    color = Color.White
                )
                Text(
                    weatherData.time,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Icon(
            imageVector = vectorResource(weatherType.iconRes),
            contentDescription = "",
            Modifier.align(Alignment.End)
                .size(120.dp),
            tint = Color.White,
        )
        Spacer(Modifier.height(10.dp))
        Button(
            onClick = { onShowDetail(weatherData, colorCode) },
            colors = ButtonDefaults.buttonColors(backgroundColor = colors.first),
            border = BorderStroke(2.dp, Color.White),
            shape = RoundedCornerShape(30.dp)
        ) {
            Row(
                Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "View", color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = vectorResource(Res.drawable.baselin_navigate_next_24),
                    contentDescription = "more",
                    tint = Color.White
                )
            }
        }
    }
}