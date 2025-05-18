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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.example.safetynet.ui.theme.SafetyNetTheme

enum class Screen {
    Home, History
}

class MainActivity : ComponentActivity() {

    val homeViewModel: HomeViewModel = HomeViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafetyNetTheme {
                val navController = rememberNavController()
                var currentScreen by remember { mutableStateOf(Screen.Home) }
                val currentRoute = currentRoute(navController)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (homeViewModel.userTitle == UserTitle.Patient && (currentRoute == "home" || currentRoute == "history")) {

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
                                                Icon(
                                                    Icons.Default.ArrowBack,
                                                    contentDescription = "Back"
                                                )
                                            }
                                        }
                                    },
                                    actions = {
                                        if (currentRoute != "settings") {
                                            Button(onClick = {
                                                navController.navigate("settings")
                                            }) {
                                                Text(
                                                    "Settings",
                                                    color = MaterialTheme.colorScheme.onPrimary
                                                )
                                            }
                                        }
                                    }
                                )
                        } else if (homeViewModel.userTitle == UserTitle.Caretaker && (currentRoute != "register" && currentRoute != "register-caretaker" && currentRoute != "login" && currentRoute != "login-patient" && currentRoute != "settings" && currentRoute != "patient-detail/{patientId}")) {
                            TopAppBar(
                                title = {
                                    Text(
                                        "List of Patients"
                                    )
                                },
                                modifier = Modifier.padding(horizontal = 8.dp),
                                navigationIcon = {
                                    if (currentRoute == "settings") {
                                        IconButton(onClick = { navController.popBackStack() }) {
                                            Icon(
                                                Icons.Default.ArrowBack,
                                                contentDescription = "Back"
                                            )
                                        }
                                    }
                                },
                                actions = {
                                    if (currentRoute != "settings") {
                                        Button(onClick = {
                                            navController.navigate("settings")
                                        }) {
                                            Text(
                                                "Settings",
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    },
                    bottomBar = {
                        if (homeViewModel.userTitle == UserTitle.Patient) {
                            if (currentRoute == "home" || currentRoute == "history") {
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
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") { LoginScreen(navController) }
                        composable("home") { HomeScreen() }
                        composable("history") { HistoryScreen(modifier = Modifier.fillMaxSize()) }
                        composable("settings") { SettingsScreen(navController) }
                        composable("register") { RegisterScreen(homeViewModel, navController) }
                        composable("login-patient") { LoginPatientScreen(homeViewModel, navController) }
                        composable("register-caretaker") { RegisterCaretaker(homeViewModel, navController) }
                        composable("patient-list") { PatientListScreen(navController) }
                        composable(
                            route = "patient-detail/{patientId}",
                            arguments = listOf(navArgument("patientId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val patientId = backStackEntry.arguments?.getString("patientId")
                            PatientDetailScreen(patientId = patientId, navController = navController)
                        }
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
