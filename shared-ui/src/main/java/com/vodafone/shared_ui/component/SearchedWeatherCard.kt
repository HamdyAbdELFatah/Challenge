package com.vodafone.shared_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.vodafone.core.models.Weather
import com.vodafone.weather_lib.getIconUrl

@Composable
fun SearchedWeatherCard(weather: Weather,title:String?=null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = title?:"Last Searched Weather", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weather.city,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold, fontSize =  16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = com.vodafone.shared_ui.R.drawable.ic_temperature),
                    contentDescription = "Example Vector Image",
                )
                Text(
                    text = "${weather.temperature}°C",
                    style = MaterialTheme.typography.labelMedium, fontSize =  16.sp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = weather.condition, style = MaterialTheme.typography.bodySmall, fontSize =  16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = weather.icon.getIconUrl(),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(64.dp)

            )


        }
    }
}
