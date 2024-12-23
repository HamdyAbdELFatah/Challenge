import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.vodafone.core.models.DailyForecast
import com.vodafone.weather_lib.getIconUrl

@Composable
fun WeatherCard(dailyForecast: DailyForecast) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp)
                .padding(bottom = 6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                WeatherIcon(dailyForecast.icon)
                Text(
                    text = dailyForecast.weatherCondition,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.weight(1f)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = com.vodafone.shared_ui.R.drawable.ic_temperature),
                        contentDescription = "Example Vector Image",
                    )
                    Text(
                        text = "${dailyForecast.temperature}°C",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = dailyForecast.weatherDescription,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(top = 3.dp, start = 12.dp)
                )
                Text(
                    text = dailyForecast.dateTime,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(top = 3.dp,start = 12.dp)
                )

            }
        }
    }
}

@Composable
fun WeatherIcon(iconName: String) {
    AsyncImage(
        model = iconName.getIconUrl(),
        contentDescription = "Weather Icon",
        modifier = Modifier.size(54.dp),
    )
}


@Preview(showBackground = true)
@Composable
fun WeatherCardPreview() {
    val forecast = DailyForecast(
        dateTime = "2024-12-23",
        temperature = 17.0,
        weatherCondition = "Rain",
        weatherDescription = "Heavy rain with occasional thunderstorms",
        icon = "rain_icon"
    )
    WeatherCard(dailyForecast = forecast)

}
