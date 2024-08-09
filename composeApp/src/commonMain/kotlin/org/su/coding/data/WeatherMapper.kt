package data

import domain.WeatherData
import domain.WeatherType
import util.dateCheck
import util.formatIsoStringToReadableDate

fun WeatherDataDto.toWeatherInfo(): List<WeatherData> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]

        WeatherData(
            date = dateCheck(time),
            time = formatIsoStringToReadableDate(time),
            temperatureCelsius = temperature,
            pressure = pressure,
            windSpeed = windSpeed,
            humidity = humidity,
            weatherCode = weatherCode,
        )
    }.distinctBy { it.time }
}