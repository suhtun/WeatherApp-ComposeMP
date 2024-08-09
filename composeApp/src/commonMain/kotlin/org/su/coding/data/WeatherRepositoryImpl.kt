package data

import domain.WeatherData
import domain.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.NetworkError
import util.Result

class WeatherRepositoryImpl(private val httpClient: HttpClient) : WeatherRepository{

//    private val httpClient: HttpClient by inject()

    override suspend fun getForecastDays(): Result<List<WeatherData>, NetworkError> {
        val response = try {
            httpClient.get(urlString = "https://api.open-meteo.com/v1/forecast?latitude=16.8409&longitude=96.1735&forecast_days=7&hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when (response.status.value) {
            in 200..299 -> {
                val result = response.body<WeatherDto>()
                Result.Success(result.hourly.toWeatherInfo())
            }

            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

}