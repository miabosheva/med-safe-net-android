package com.example.safetynet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.safetynet.ui.theme.SafetyNetTheme

enum class Screen {
    Home, History
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafetyNetTheme {
                val navController = rememberNavController()
                var currentScreen by remember { mutableStateOf(Screen.Home) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        val currentRoute = currentRoute(navController)
                        TopAppBar(
                            title = {
                                Text(
                                    when (currentRoute) {
                                        "settings" -> "Back"
                                        else -> currentScreen.name
                                    }
                                )
                            },
                            modifier = Modifier.padding(horizontal = 8.dp),
                            navigationIcon = {
                                if (currentRoute == "settings") {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                                    }
                                }
                            },
                            actions = {
                                if (currentRoute != "settings") {
                                    Button(onClick = {
                                        navController.navigate("settings")
                                    }) {
                                        Text("Settings", color = MaterialTheme.colorScheme.onPrimary)
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        BottomAppBar {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                BottomNavButton(
                                    selected = currentScreen == Screen.History,
                                    onClick = {
                                        currentScreen = Screen.History
                                        navController.navigate("history") {
                                            popUpTo("home") { inclusive = false }
                                        }
                                    },
                                    icon = Icons.Default.FavoriteBorder,
                                    label = "History",
                                    modifier = Modifier.weight(1f)
                                )
                                BottomNavButton(
                                    selected = currentScreen == Screen.Home,
                                    onClick = {
                                        currentScreen = Screen.Home
                                        navController.navigate("home") {
                                            popUpTo("home") { inclusive = true }
                                        }
                                    },
                                    icon = Icons.Outlined.Home,
                                    label = "Home",
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") { HomeScreen() }
                        composable("history") { HistoryScreen(modifier = Modifier.fillMaxSize()) }
                        composable("settings") { SettingsScreen(navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
