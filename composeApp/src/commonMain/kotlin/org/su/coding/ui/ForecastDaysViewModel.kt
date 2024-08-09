import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.WeatherRepositoryImpl
import domain.WeatherData
import domain.WeatherRepository
import kotlinx.coroutines.launch
import util.Result

class ForecastDaysViewModel(
    private val repo: WeatherRepository
) : ViewModel() {

    var state by mutableStateOf(ForecastDaysState())
        private set

    init {
        loadForecastDays()
    }

    fun loadForecastDays() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)

            when (val result = repo.getForecastDays()) {
                is Result.Success -> {
                    state = state.copy(
                        isLoading = false,
                        weatherDatas = result.data,
                        error = null
                    )
                }

                is Result.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.error.name
                    )
                }
            }
        }
    }

}

data class ForecastDaysState(
    val isLoading: Boolean = false,
    val weatherDatas: List<WeatherData> = emptyList(),
    val error: String? = null
)