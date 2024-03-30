package view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.compose.koinInject
import viewmodel.search.SearchDefault
import viewmodel.search.SearchFailed
import viewmodel.search.SearchLoading
import viewmodel.search.SearchSuccess
import viewmodel.search.SearchViewModel
import viewmodel.theme.ThemeViewModel

class HomeScreen(val themeViewModel: ThemeViewModel) : Screen {
    @Composable
    override fun Content() {
        SearchScreen(themeViewModel = themeViewModel)
    }

    @Composable
    fun SearchScreen(searchViewModel: SearchViewModel = koinInject(),themeViewModel: ThemeViewModel) {
        val themeState = themeViewModel.isDarkTheme.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        var search by remember { mutableStateOf("") }
        val searchState by searchViewModel.searchState.collectAsState()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Weather Application")
                    },
                    actions = {
                       Text("Dark Theme")
                        Switch(
                            checked = themeState.value,
                            onCheckedChange = {
                                themeViewModel.changeTheme(it)
                            }
                        )
                    }
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Blue,
                        disabledBorderColor = Color.Gray,
                        errorBorderColor = Color.Red,
                    ),
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
                        items(results?.size ?: 0) {
                            val name = results?.get(it)?.name
                            val code = results?.get(it)?.country_code
                            val latitude = results?.get(it)?.latitude
                            val longitude = results?.get(it)?.longitude
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (latitude != null && longitude != null && name != null)
                                            navigator.push(
                                                WeatherDetailScreen(
                                                    latitude,
                                                    longitude,
                                                    true,
                                                    name,
                                                )
                                            )
                                    }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (code != null)
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
}