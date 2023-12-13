package com.example.roomsiswa.Model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomsiswa.Repository.RepositorySiswa
import com.example.roomsiswa.ui.theme.Halaman.DetailDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Stack

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel(){

    private val siswaId: Int = checkNotNull(savedStateHandle[DetailDestination.siswaIdArg])
    val uiState: StateFlow<ItemDetailUiState> =
        repositorySiswa.getSiswaStream(siswaId)
            .filterNotNull()
            .map {
                ItemDetailUiState(detailSiswa = it.toDetailSiswa())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILIS),
            initialValue = ItemDetailUiState()
        )

    suspend fun deleteItem(){
        repositorySiswa.deleteSiswa(uiState.value.detailSiswa.toSiswa())
    }

    companion object{
        private const val TIMEOUT_MILIS = 5_000L
    }
}

data class ItemDetailUiState(
    val outOfStack: Boolean = true,
    val detailSiswa: DetailSiswa = DetailSiswa(),
)

