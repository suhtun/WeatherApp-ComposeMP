package org.su.coding

import ForecastDaysViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModel { ForecastDaysViewModel(get()) }
}