package domain

import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val date: String,
    val time:String,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    val weatherCode: Int
//    val weatherType: WeatherType
)
