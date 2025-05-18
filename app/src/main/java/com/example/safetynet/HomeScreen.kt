package com.example.safetynet
import android.bluetooth.BluetoothClass.Device
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ovaa data treba za view
class HomeScreenData {
//    var user
    var heartRate: Int = 90
    var deviceConnection: DeviceConnection = DeviceConnection.CONNECTED
//    var event:
}

enum class DeviceConnection {
    CONNECTED, NOT_CONNECTED
}

@Composable
fun HomeScreen(homeScreenData: HomeScreenData = HomeScreenData(), modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val totalHeight = maxHeight

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Heart Rate
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(totalHeight * 0.4f)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
                    .padding(top = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(modifier = Modifier.fillMaxWidth().height(95.dp)) {
                        Text("Welcome", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Today is 17.05.2025", fontSize = 22.sp, fontWeight = FontWeight.Light)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Your Heart Rate is", fontSize = 28.sp, fontWeight = FontWeight.Normal)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(homeScreenData.heartRate.toString(), fontSize = 58.sp, fontWeight = FontWeight.Bold)
                        Text("bpm", fontSize = 26.sp, fontWeight = FontWeight.Normal)
                    }
                }
            }

            // Device Connection
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(totalHeight * 0.30f)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Device Connection Information",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Divider(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Device", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        Text("SafeTrack-001", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Status", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        Text(
                            homeScreenData.deviceConnection.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50)
                        )
                    }

//                    Spacer(modifier = Modifier.height(22.dp))
                    Divider(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )

                    Text(
                        "Battery",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LinearProgressIndicator(
                        progress = 0.65f, // 65% battery level
                        color = Color(0xFF4CAF50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .padding(top = 4.dp),
                    )
                }
            }

            // 2/5 - Latest Event
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(totalHeight * 0.30f)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Latest Event",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Divider(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Date", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        Text("17.05.2025", fontSize = 18.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Time", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        Text("17:05", fontSize = 18.sp)
                    }

                    Divider(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Status", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                        Text("DECLINED", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                    }
                }
            }
        }
    }
}