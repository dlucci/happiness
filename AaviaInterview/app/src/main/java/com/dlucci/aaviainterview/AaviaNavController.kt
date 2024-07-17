package com.dlucci.aaviainterview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dlucci.aaviainterview.ui.theme.Selected
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue



@Composable
fun AaviaNavController(navController: NavHostController) {

    NavHost(navController = navController, startDestination = AaviaNavigationValues.home.route) {
        composable(AaviaNavigationValues.home.route) {
            HomeScreen(navController)
        }

        composable(AaviaNavigationValues.favorite.route) {
            FavoriteScreen(navController)
        }
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "What made you happy today?",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        val buttonList = remember {
            mutableListOf("Family", "Friends", "Work", "Other...")
        }
        var showDialog by rememberSaveable {
            mutableStateOf(false)
        }

        var activeElements by remember { viewModel.activeElement }
        val selectedColor = Selected

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            buttonList.forEach {
                SuggestionChip(onClick = {
                    if (it == "Other...") {
                        showDialog = true
                    } else {
                        if (activeElements.contains(it)) {
                            activeElements = activeElements - it
                        } else {
                            activeElements = activeElements + it
                        }
                    }
                }, label = { Text(text = it) },
                    colors = SuggestionChipDefaults.suggestionChipColors(containerColor = if (activeElements.contains(it)) selectedColor else Color.White)
                )
            }
        }

        if (showDialog) {
            InputAlertDialog(onDismissRequest = {
                showDialog = false
            },
                onConfirmAction = { newField ->
                    buttonList.add(buttonList.size-1, newField)
                    viewModel.activeActiveElements(activeElements)
                    showDialog = false
                })
        }

        
        Button(onClick = { viewModel.activeActiveElements(activeElements) }) {
            Text(text = "Save")
        }
    }

}

@Composable
fun InputAlertDialog(onDismissRequest: () -> Unit, onConfirmAction: (String) -> Unit) {

    var newField by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        dismissButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("Cancel")
            }
        },
        confirmButton = {
            Button(onClick = { onConfirmAction(newField) }) {
                Text("Add")
            }
        },
        title = { Text("Add \"other\" option") },
        text = {
            TextField(
                value = newField,
                onValueChange = { newField = it },
                label = { Text("Enter new field") })
        })
}

@Composable
fun FavoriteScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Another Tab",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FavoriteScreenPreview() {
    val navController = rememberNavController()
    FavoriteScreen(navController = navController)
}
