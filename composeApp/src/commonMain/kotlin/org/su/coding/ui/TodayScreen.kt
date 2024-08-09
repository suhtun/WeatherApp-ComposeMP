package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.WeatherData
import domain.WeatherType
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.su.coding.util.getBackgroundColor
import ui.theme.Purpel
import weatherapp.composeapp.generated.resources.Res
import weatherapp.composeapp.generated.resources.baseline_arrow_back
import weatherapp.composeapp.generated.resources.baseline_location_on
import weatherapp.composeapp.generated.resources.humidity
import weatherapp.composeapp.generated.resources.ic_heavysnow
import weatherapp.composeapp.generated.resources.weather
import weatherapp.composeapp.generated.resources.wind
import kotlin.math.roundToInt

@Composable
fun TodayScreen(
    weatherData: WeatherData,
    colorCode: Int,
    onBack: () -> Unit
) {

    val colors = getBackgroundColor(colorCode)
    Column(
        Modifier.fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(colors.first, colors.second)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TopBar(onBack = {onBack()})
        Column(
            Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val weatherType = WeatherType.fromWMO(weatherData.weatherCode)
            Spacer(Modifier.height(30.dp))
            Icon(
                imageVector = vectorResource(weatherType.iconRes),
                contentDescription = "",
                tint = Color.White
            )
            Spacer(Modifier.height(20.dp))
            Text(
                "${weatherData.temperatureCelsius}°",
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                weatherType.weatherDesc,
                fontSize = 40.sp,
                color = Color.White
            )
            Text(
                weatherData.time,
                fontSize = 18.sp,
                color = Color.White
            )
        }
        WeatherInfoCard(
            Modifier.height(160.dp),
            weatherData.humidity.roundToInt(),
            weatherData.pressure.roundToInt(),
            weatherData.windSpeed.roundToInt()
        )
    }
}

@Composable
fun WeatherInfoCard(
    modifier: Modifier = Modifier,
    humidity: Int,
    pressure: Int,
    windSpeed: Int
) {

    Row(
        modifier.fillMaxWidth()
            .background(Color.White, RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.width(20.dp))
        WeatherInfoItem(
            Res.drawable.humidity,
            unit = "${humidity}%",
            label = "Humidity"
        )
        WeatherInfoItem(
            Res.drawable.weather,
            unit = "${pressure}hpa",
            label = "Chance of rain"
        )
        WeatherInfoItem(
            Res.drawable.wind,
            unit = "${windSpeed}km/h",
            label = "Wind"
        )
        Spacer(Modifier.width(20.dp))
    }
}

@Composable
fun WeatherInfoItem(
    icon: DrawableResource,
    unit: String,
    label: String,

    ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painterResource(icon), "",
            Modifier.size(48.dp)
        )
        Text(unit, fontSize = 22.sp)
        Text(label)
    }
}

@Composable
private fun TopBar(onBack: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.baseline_arrow_back),
            contentDescription = "back",
            tint = Color.White,
            modifier = Modifier.size(18.dp).clickable { onBack() }
        )

        Row(Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)) {
            Icon(
                vectorResource(Res.drawable.baseline_location_on),
                contentDescription = "location",
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
            Text(
                "Yangon",
                fontSize = 24.sp,
                color = Color.White
            )
        }
    }
}