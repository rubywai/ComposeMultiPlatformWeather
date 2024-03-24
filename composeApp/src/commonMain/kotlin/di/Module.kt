
import data.service.ApiService
import di.viewModelDefinition
import org.koin.dsl.module
import viewmodel.search.SearchViewModel
import viewmodel.theme.ThemeViewModel
import viewmodel.weather_detail.WeatherDetailViewModel

fun appModule() = module{
    single { ApiService() }
    viewModelDefinition { SearchViewModel() }
    viewModelDefinition { WeatherDetailViewModel() }
    viewModelDefinition { ThemeViewModel() }
}