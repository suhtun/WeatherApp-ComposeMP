import data.WeatherRepositoryImpl
import data.createWeatherHttpClient
import domain.WeatherRepository
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val appModule = module {
    single {
        createWeatherHttpClient(Darwin.create())
    }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }

    singleOf(::ForecastDaysViewModel)
}