package com.bornahoosh.afraagpstracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bornahoosh.afraagpstracker.ui.theme.AfraaGPSTrackerTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

class MainActivity : ComponentActivity() {
    private val Context.dataStore by preferencesDataStore(name= "data_store")
    private val topBarTitleKey = stringPreferencesKey("device_number")
    private val topBarSubTitleKey = stringPreferencesKey("device_id")

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        lifecycleScope.launch {
            viewModel.loadTopBarTitle(dataStore, topBarTitleKey,topBarSubTitleKey)
        }
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            val topBarTitle by viewModel.topBarTitle.collectAsState(initial = "Device Number")
            val topBarSubTitle by viewModel.topBarSubTitle.collectAsState(initial = "Device ID")

            Log.d("hhh", "onCreate: $topBarTitle")
            AfraaGPSTrackerTheme {
                Scaffold(topBar = { AppBar(topBarTitle,topBarSubTitle) }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AfraaGPSTrackerTheme {
        Greeting("Android")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(topBarTitle: String,subtitle: String) {
    TopAppBar(
        title =  {
            Column {
                Text(
                    text = topBarTitle,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

    )
}



class MainViewModel : ViewModel() {

    private val _topBarTitle = MutableStateFlow("device number")
    private val _topBarSubTitle = MutableStateFlow("device id")

    val topBarTitle: StateFlow<String> = _topBarTitle
    val topBarSubTitle: StateFlow<String> = _topBarSubTitle


    fun loadTopBarTitle(dataStore: DataStore<Preferences>, keyTitle: Preferences.Key<String>, keySubtitle: Preferences.Key<String>) {
        viewModelScope.launch {
            dataStore.data
                .catch { exception ->
                    // Handle exceptions here
                    Log.e("DataStore", "Error reading data from DataStore", exception)
                    emit(emptyPreferences())
                }
                .map { preferences ->
                    Pair(preferences[keyTitle] ?: "09333333333", preferences[keySubtitle] ?: "3029A59A59BD7")
                }
                .collect { (title, subtitle) ->
                    _topBarTitle.value = title
                    _topBarSubTitle.value = subtitle // Update subtitle state
                }
        }
    }
}