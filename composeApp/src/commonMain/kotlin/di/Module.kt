
import data.service.ApiService
import org.koin.dsl.module
import viewmodel.theme.ThemeViewModel

fun appModule() = module{
    single { ApiService() }
    single { ThemeViewModel() }
}