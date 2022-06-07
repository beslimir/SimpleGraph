package com.example.simplegraph

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class NumberGenerator: ViewModel() {

    var state by mutableStateOf(NumberState())

    init {
        viewModelScope.launch {
            for (i in 1 until 10) {
                val randomNum = Random.nextInt(1, 10)
                state = state.copy(
                    generatedList = state.generatedList.plus(randomNum).toMutableList()
                )
                delay(1000L)
            }
            Log.d("points", "${state.generatedList.toList()}")
        }
    }


}