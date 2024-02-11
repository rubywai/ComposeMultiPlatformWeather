
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import viewmodel.search.SearchDefault
import viewmodel.search.SearchFailed
import viewmodel.search.SearchLoading
import viewmodel.search.SearchSuccess
import viewmodel.search.SearchViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        Home()
    }
}

@Composable
fun Home() {
    SearchScreen()
}

@Composable
fun SearchScreen() {
    var search by remember { mutableStateOf("") }
    val searchViewModel = getViewModel(Unit, viewModelFactory { SearchViewModel() })
    val searchState by searchViewModel.searchState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Weather Application")
                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TextField(
                value = search,
                onValueChange = { search = it },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            searchViewModel.search(search)
                        },
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                },
                label = { Text("Search City") },
                modifier = Modifier.fillMaxWidth()
            )
            when (searchState) {
                is SearchLoading -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                is SearchSuccess -> LazyColumn {
                   val results = (searchState as SearchSuccess).searchModel.results
                    items(results?.size ?: 0){
                        val name = results?.get(it)?.name
                        val code = results?.get(it)?.country_code
                        Card {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if(code != null)
                                FlagImage(countryCode = code)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(name ?: "")
                            }
                        }
                    }
                }
                is SearchFailed -> Text("Error:")
                is SearchDefault -> Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                    Text("Search")
                }
            }

        }

    }
}
@Composable
fun FlagImage(countryCode: String) {
    KamelImage(
        resource = asyncPainterResource("https://flagsapi.com/${countryCode}/flat/64.png"),
        contentDescription = countryCode,
        contentScale = ContentScale.Crop,
        modifier = Modifier.width(100.dp),
    )
}