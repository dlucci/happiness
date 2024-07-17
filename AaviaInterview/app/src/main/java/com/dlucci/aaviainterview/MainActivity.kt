package com.dlucci.aaviainterview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dlucci.aaviainterview.ui.theme.AaviaInterviewTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(bottomBar = { BottomNavBar(navController) }) {
                AaviaNavController(navController = navController)
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(AaviaNavigationValues.home, AaviaNavigationValues.favorite)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentEntry = navBackStackEntry?.destination?.route
    NavigationBar {
        items.forEach {
            NavigationBarItem(
                selected = currentEntry == it.route,
                onClick = { navController.navigate(it.route) },
                icon = { },
                label = { Text(it.label)})
        }
    }

}