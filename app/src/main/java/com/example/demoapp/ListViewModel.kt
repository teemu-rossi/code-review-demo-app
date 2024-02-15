package com.example.demoapp

import androidx.lifecycle.ViewModel
import java.time.LocalDate

class ListViewModel : ViewModel() {
    val datesHours = (0L until 23L).map {
        LocalDate.now().atStartOfDay().plusHours(it)
    }
}
