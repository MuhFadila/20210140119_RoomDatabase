package com.example.roomsiswa.ui.theme.Halaman

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Delete
import com.example.roomsiswa.Data.Siswa
import com.example.roomsiswa.Model.DetailViewModel
import com.example.roomsiswa.Model.ItemDetailUiState
import com.example.roomsiswa.Model.PenyediaViewModel
import com.example.roomsiswa.Navigasi.DestinasiNavigasi
import com.example.roomsiswa.Navigasi.SiswaTopAppBar
import com.example.roomsiswa.R
import kotlinx.coroutines.launch

object DetailDestination : DestinasiNavigasi{

    override val route = "item_details"
    override val titleRes = R.string.detail_siswa
    const val siswaIdArg = "itemId"
    val routeWithArgs = "$route/{$siswaIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            SiswaTopAppBar(title = stringResource(DetailDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditItem(uiState.value.detailSiswa.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(
                dimensionResource(id = R.dimen.padding_large))
            ){
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_siswa),
                )
            }
            }, modifier = modifier
    ){ innerPadding ->
        ItemDetailsBody(
            itemDetailUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteItem()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            )
        }
}

@Composable
private fun ItemDetailsBody(
    itemDetailUiState: ItemDetailUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier){
        Text(text = stringResource(labelRestID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ItemDetails(
    siswa: Siswa, modifier: Modifier = Modifier
){
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primaryContainer
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ){
            ItemDetailsRow(labelRestID = R.string.nama, itemDetail = siswa.nama,
                modifier= Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                ))
            ItemDetailsRow(labelRestID = R.string.alamat, itemDetail = siswa.alamat,
                modifier= Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                ))
            ItemDetailsRow(labelRestID = R.string.telpon, itemDetail = siswa.telpon,
                modifier= Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                ))
            Column (
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ){
              var deleteConfirmationRequired by rememberSaveable {mutableStateOf(false)}
              ItemDetails(
                  siswa = ItemDetailUiState.detailSiswa.toSiswa(),
                  modifier = Modifier.fillMaxWidth()
              )
                OutlinedButton(
                    onClick = {deleteConfirmationRequired = true},
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()) {

                }
            }
        }
    }
}

@Composable
private fun ItemDetailsRow(
    @StringRes labelRestID: Int, itemDetail: String, modifier: Modifier = Modifier
){
    Row(modifier = modifier) {
        Text(text = stringResource(labelRestID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier

){
    AlertDialog(onDismissRequest = { /*Do Nothing*/ },
        title = { Text(stringResource(R.string.attention))},
        text = { Text(stringResource(R.string.delete))},
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))

            }
        }
    )
}