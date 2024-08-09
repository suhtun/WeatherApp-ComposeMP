package domain

import util.NetworkError
import util.Result

interface WeatherRepository {

    suspend fun getForecastDays(): Result<List<WeatherData>, NetworkError>
}