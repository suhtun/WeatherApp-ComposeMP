package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    @SerialName("hourly")
    val hourly: WeatherDataDto
)

@Serializable
data class WeatherDataDto(
    @SerialName("time")
    val time: List<String>,
    @SerialName("temperature_2m")
    val temperatures: List<Double>,
    @SerialName("weathercode")
    val weatherCodes: List<Int>,
    @SerialName("pressure_msl")
    val pressures: List<Double>,
    @SerialName("windspeed_10m")
    val windSpeeds: List<Double>,
    @SerialName("relativehumidity_2m")
    val humidities: List<Double>
)