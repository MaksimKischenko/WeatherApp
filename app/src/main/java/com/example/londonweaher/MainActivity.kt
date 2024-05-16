package com.example.londonweaher

import HeaderElement
import TabElement
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.londonweaher.models.WeatherModel
import com.example.londonweaher.ui.theme.LondonWeaherTheme
import com.example.londonweaher.utils.DateFormat
import com.example.londonweaher.utils.TempFormat
import com.example.weatherapp.widgets.DialogSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

const val API_KEY = "726498cce66e3f0b3988188ae6e7e4fc"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LondonWeaherTheme {
                val weatherModel = remember {
                    mutableStateOf(WeatherModel())
                }
                val dialogState = remember {
                    mutableStateOf(false)
                }
                if(dialogState.value) {
                    DialogSearch(dialogState, onSubmit = {
                        getWeatherData(it, this@MainActivity, weatherModel)
                    })
                }
                LaunchedEffect(Unit) {
                    withContext(Dispatchers.IO) {
                        getWeatherData("London", this@MainActivity, weatherModel)
                    }
                }
                androidx.compose.material.Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material.MaterialTheme.colors.background,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img),
                        contentDescription = "Img1",
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.7f),
                        contentScale = ContentScale.FillBounds
                    )
                    Column {
                        HeaderElement(
                            weatherModel,
                            onClickSync = {
                                getWeatherData("", this@MainActivity, weatherModel)
                            },
                            onClickSearch = {
                                dialogState.value = true
                            }
                        )
                        TabElement(weatherModel)
                    }
                }
            }
        }
    }
}

private fun getWeatherData(
    city: String,
    context: Context,
    state: MutableState<WeatherModel>,
) {
    val url = "https://api.openweathermap.org/data/2.5/weather?q=$city,uk&APPID=$API_KEY"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
            response -> state.value = getWeather(response)
            Log.d("MyLog", "response $response")
        },
        { error-> Log.d("MyLog", "Error $error")}
    )
    queue.add(stringRequest)
}

private fun getWeather(response: String): WeatherModel {
    if(response.isEmpty()) return WeatherModel()
    val jsonResponse =  JSONObject(response)
    return WeatherModel(
        city = jsonResponse.getString("name"),
        currentTemp = TempFormat.formKelvinToCelsius(jsonResponse.getJSONObject("main").getString("temp")),
        minTemp = TempFormat.formKelvinToCelsius(jsonResponse.getJSONObject("main").getString("temp_min")),
        maxTemp = TempFormat.formKelvinToCelsius(jsonResponse.getJSONObject("main").getString("temp_max")),
        timeDate = DateFormat.formDate(jsonResponse.getString("dt")),
        description = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description"),
        mainDesc = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("main"),
        icon = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("icon"),
        pressure =  jsonResponse.getJSONObject("main").getString("pressure"),
        windSpeed = jsonResponse.getJSONObject("wind").getString("speed"),
        sunrise = DateFormat.formDate(jsonResponse.getJSONObject("sys").getString("sunrise")),
        sunset = DateFormat.formDate(jsonResponse.getJSONObject("sys").getString("sunset")),
    )
}
