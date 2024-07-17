package com.dlucci.aaviainterview

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _buttonList by lazy {
        MutableStateFlow(listOf("Family", "Friends", "Work", "Other..."))
    }


    init {

    }

    private val _activeElements by lazy {
        mutableStateOf<Set<String>>(emptySet())
    }

    init {
        if(!sharedPreferences.contains("aavia")) {
            _activeElements.value = emptySet()
        } else {
            _activeElements.value = sharedPreferences.getStringSet("aavia", emptySet())!!
        }
    }

    val activeElement : MutableState<Set<String>>
        get() = _activeElements


    fun activeActiveElements(newElements : Set<String>) {
        _activeElements.value = newElements
        sharedPreferences.edit().putStringSet("aavia", newElements).apply()
    }
}
