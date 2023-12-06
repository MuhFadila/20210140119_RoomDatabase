package com.example.roomsiswa.Model

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.roomsiswa.AplikasiSiswa

object PenyediaViewModel {
    val Factory = viewModelFactory {

        initializer {
            HomeViewModel(AplikasiSiswa().container.repositorySiswa)
        }

        initializer {
            EntryViewModel(AplikasiSiswa().container.repositorySiswa)
        }

    }


}

