package viewmodel.search

import data.service.ApiService
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel : ViewModel(),KoinComponent {
    private val _searchState = MutableStateFlow<SearchState>(SearchDefault)
    val searchState = _searchState.asStateFlow()
    private val apiService by inject<ApiService>()
    fun search( name : String){
      viewModelScope.launch {
          _searchState.value = SearchLoading
          try {
              val cities = apiService.searchCity(name)
              _searchState.update {
                  SearchSuccess(cities)
              }
          }
          catch (e: Exception){
              _searchState.update { SearchFailed("Something wrong") }
          }
      }
    }
}