package com.example.roomsiswa.Model

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
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
        initializer {
            DetailViewModel(createSavedStateHandle(), aplikasiSiswa().container.repositorySiswa)
        }
        initializer {
            EditViewModel(createSavedStateHandle(), aplikasiSiswa().container.repositorySiswa)
        }

    }

    fun CreationExtras.aplikasiSiswa():AplikasiSiswa =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiSiswa)
}

