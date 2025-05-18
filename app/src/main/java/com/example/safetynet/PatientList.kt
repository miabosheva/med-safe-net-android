package com.example.safetynet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

data class Patient(val id: Int, val name: String)

class PatientListViewModel : ViewModel() {
    private var nextId = 1
    var patients = mutableStateListOf<Patient>()
        private set

    fun addPatient(name: String) {
        patients.add(Patient(nextId++, name))
    }

    fun deletePatient(patient: Patient) {
        patients.remove(patient)
    }
}

@Composable
fun PatientListScreen(
    navController: NavController,
    viewModel: PatientListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var newName by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Input row
        Row {
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Code") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(56.dp),
                onClick = {
                    if (newName.isNotBlank()) {
                        viewModel.addPatient(newName.trim())
                        newName = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Patient List", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(viewModel.patients, key = { it.id }) { patient ->
                SwipeToDeleteCard(
                    patient = patient,
                    onDelete = { viewModel.deletePatient(patient) },
                    onDetail = { navController.navigate("patient-detail/${patient.id}") }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
@Composable
fun SwipeToDeleteCard(
    patient: Patient,
    onDelete: () -> Unit,
    onDetail: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX < -150f) onDelete()
                        offsetX = 0f
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        offsetX += dragAmount
                    }
                )
            }
    ) {
        // Swipe-to-delete background
        if (offsetX < -20f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Red)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text("Deleting...", color = Color.White)
            }
        }

        // Foreground card
        Card(
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), 0) }
                .fillMaxWidth()
                .clickable { onDetail() }
                .padding(horizontal = 8.dp), // Slight inset
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = patient.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "View details",
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
