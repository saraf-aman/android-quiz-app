package edu.vt.cs5254.multiquiz
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {
    var reset = false

    fun resetClicked() {
        reset = true
    }
}