package viewmodel.search

import data.model.search.CitySearchModel

sealed class SearchState
data object SearchDefault : SearchState()
data object SearchLoading : SearchState()
class SearchSuccess(val searchModel: CitySearchModel) : SearchState()
class SearchFailed(val errorMessage : String) : SearchState()