package com.example.safetynet
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

        // Input field to add a new patient
        Row {
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("New Patient") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newName.isNotBlank()) {
                    viewModel.addPatient(newName.trim())
                    newName = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Patient List", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(viewModel.patients) { patient ->
                PatientItem(
                    patient = patient,
                    onDelete = { viewModel.deletePatient(patient) },
                    onDetail = {
                        // Example: Navigate to detail with patient ID or name
                        navController.navigate("patient-detail/${patient.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun PatientItem(
    patient: Patient,
    onDelete: () -> Unit,
    onDetail: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetail() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = patient.name, modifier = Modifier.weight(1f))
        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}


