package com.dlucci.aaviainterview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import com.dlucci.aaviainterview.ui.theme.AlertButtonColor
import com.dlucci.aaviainterview.ui.theme.ButtonColor


@Composable
fun AaviaNavController(navController: NavHostController, padding : PaddingValues) {

    NavHost(navController = navController, startDestination = AaviaNavigationValues.home.route) {
        composable(AaviaNavigationValues.home.route) {
            HomeScreen(navController, padding = padding)
        }

        composable(AaviaNavigationValues.favorite.route) {
            FavoriteScreen(navController)
        }
    }

}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel(), padding: PaddingValues,) { //total : Float = 12.53f) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = padding.calculateBottomPadding())
    ) {
        Text(
            text = "What made you happy today?",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
        )


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
                            viewModel.removeActiveElements(it)
                        } else {
                            viewModel.addActiveElements(it)
                        }
                    }
                }, label = { Text(text = it, fontSize = 14.sp) },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = if (activeElements.contains(
                                it
                            )
                        ) selectedColor else Color.White
                    )
                )
            }
        }

        if (showDialog) {
            InputAlertDialog(onDismissRequest = {
                showDialog = false
            },
                onConfirmAction = { newField ->
                    buttonList.add(buttonList.size - 1, newField)
                    //activeElements.add(newField)
                    viewModel.activeActiveElements(activeElements)
                    showDialog = false
                })
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(onClick = { viewModel.activeActiveElements(activeElements)},
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColor, disabledContainerColor = Color.Black),
            enabled = activeElements.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)

                ) {
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
            Button(onClick = { onDismissRequest() }, content = {Text("Cancel", color = AlertButtonColor)}, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent) )

        },
        confirmButton = {
            Button(onClick = { onConfirmAction(newField) }, content = {Text("Add", color = AlertButtonColor)}, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent))
        },
        title = { Text("Add \"other\" option") },
        text = {
            TextField(
                value = newField,
                onValueChange = { newField = it },
                placeholder = { Text("Enter new field", color = Color.Gray) },)
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
