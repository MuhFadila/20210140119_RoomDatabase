package com.example.roomsiswa.Model

import androidx.lifecycle.ViewModel
import com.example.roomsiswa.Data.Siswa
import com.example.roomsiswa.Repository.RepositorySiswa
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repositorySiswa: RepositorySiswa) : ViewModel() {



    data class HomeUiState(
        val listSiswa: List<Siswa> = listOf()
    )
}