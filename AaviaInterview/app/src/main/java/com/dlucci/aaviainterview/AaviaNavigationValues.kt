package com.dlucci.aaviainterview

sealed class AaviaNavigationValues(val route: String, val label : String) {

    object home : AaviaNavigationValues("home", "Home")
    object favorite : AaviaNavigationValues("favorite", "Favorite")

}