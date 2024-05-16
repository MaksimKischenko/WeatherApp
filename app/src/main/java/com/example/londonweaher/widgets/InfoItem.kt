
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.londonweaher.models.WeatherModel
import com.example.londonweaher.ui.theme.BlueLight
import com.example.londonweaher.ui.theme.TextDark


@Composable
fun InfoItemTemperature(state: MutableState<WeatherModel>) {
    Card(
        modifier = Modifier.padding(top = 20.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = BlueLight
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
    ) {
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
      ) {
          Column(
              modifier = Modifier.padding(
                  start = 8.dp, top = 4.dp, bottom = 4.dp
              )
          ) {
              Text(
                  text = "Time:",
                  color = TextDark,
                  style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(600))
              )
              Text(
                  text =  state.value.timeDate.substring(11),
                  color = Color.White,
                  style = TextStyle(fontSize = 14.sp)
              )
          }
          Column(
              horizontalAlignment = Alignment.CenterHorizontally
          ) {
              Row() {
                  Text(
                      text = "Min/",
                      color = TextDark,
                      style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(600))
                  )
                  Text(
                      text = "Max",
                      color = TextDark,
                      style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(600))
                  )
              }
              Row() {
                  Text(
                      text = state.value.minTemp.ifEmpty {state.value.currentTemp} + "°C/",
                      color = Color.White,
                      style = TextStyle(fontSize = 24.sp)
                  )
                  Text(
                      text = state.value.maxTemp.ifEmpty {state.value.currentTemp} + "°C",
                      color = Color.White,
                      style = TextStyle(fontSize = 24.sp)
                  )
              }
          }
          Column(
              modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 8.dp),
              horizontalAlignment = Alignment.End
          ) {
              AsyncImage(
                  model = "https://openweathermap.org/img/wn/${state.value.icon}@2x.png",
                  contentDescription = "img3",
                  modifier = Modifier.size(24.dp)
              )
              Text(
                  text = state.value.description,
                  color = Color.White,
                  style = TextStyle(fontSize = 14.sp)

              )
          }

      }
    }
}

@Composable
fun InfoItemAdditionally(state: MutableState<WeatherModel>) {
    Card(
        modifier = Modifier
            .padding(top = 20.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = BlueLight
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 8.dp, top = 4.dp, bottom = 4.dp
                )
            ) {
                Text(
                    text = "Time:",
                    color = TextDark,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(600))
                )
                Text(
                    text =  state.value.timeDate.substring(11),
                    color = Color.White,
                    style = TextStyle(fontSize = 14.sp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    Text(
                        text = "Sunrise/",
                        color = TextDark,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(600))
                    )
                    Text(
                        text = "Sunset",
                        color = TextDark,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(600))
                    )
                }
                Row() {
                    Text(
                        text =  state.value.sunrise.substring(11) + "/",
                        color = Color.White,
                        style = TextStyle(fontSize = 24.sp)
                    )
                    Text(
                        text =  state.value.sunset.substring(11),
                        color = Color.White,
                        style = TextStyle(fontSize = 24.sp)
                    )
                }
            }
            Column(
                modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "WindSpeed:",
                    color = TextDark,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(600))
                )
                Text(
                    text = state.value.windSpeed + " m/s",
                    color = Color.White,
                    style = TextStyle(fontSize = 14.sp)

                )
            }

        }
    }
}