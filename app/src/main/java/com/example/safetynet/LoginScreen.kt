package com.example.safetynet

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SafetyNet", fontSize = 30.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Real time care. Anytime. Anywhere.", fontSize = 16.sp, fontWeight = FontWeight.Light, modifier = Modifier.padding(bottom = 150.dp))

        Button(
            onClick = {
                // TODO: - Simulate login success
                navController.navigate("register")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Icon(imageVector = Icons.Outlined.Favorite, contentDescription = "Patient")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log In as a Caretaker", fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Simulate login success
                navController.navigate("login-patient")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EE),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = "Patient")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log In as a Patient", fontSize = 20.sp)
            }
        }
    }
}
