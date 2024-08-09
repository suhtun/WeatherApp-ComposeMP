import androidx.compose.runtime.remember
import data.WeatherRepositoryImpl
import data.createWeatherHttpClient
import domain.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val appModule = module {

    single {
        createWeatherHttpClient(OkHttp.create())
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get())
    }


}
