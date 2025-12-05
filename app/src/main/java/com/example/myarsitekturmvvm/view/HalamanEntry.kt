package com.example.myarsitekturmvvm.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myarsitekturmvvm.R
import com.example.myarsitekturmvvm.viewmodel.DetailSiswa
import com.example.myarsitekturmvvm.viewmodel.provider.PenyediaViewModel
import com.example.myarsitekturmvvm.viewmodel.EntryViewModel
import com.example.myarsitekturmvvm.viewmodel.UIStateSiswa
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySiswaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    val uiState = viewModel.uiStateSiswa

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = uiState.detailSiswa.nama,
            onValueChange = {
                viewModel.updateUIState(uiState.detailSiswa.copy(nama = it))
            },
            label = { Text(stringResource(R.string.nama)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.detailSiswa.alamat,
            onValueChange = {
                viewModel.updateUIState(uiState.detailSiswa.copy(alamat = it))
            },
            label = { Text(stringResource(R.string.alamat)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.detailSiswa.telpon,
            onValueChange = {
                viewModel.updateUIState(uiState.detailSiswa.copy(telpon = it))
            },
            label = { Text(stringResource(R.string.telpon)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        if (!uiState.isEntryValid) {
            Text(
                text = stringResource(R.string.form_tidak_valid),
                color = androidx.compose.ui.graphics.Color.Red
            )
        }

        val scope = rememberCoroutineScope()

        Button(
            onClick = {
                scope.launch {
                    viewModel.saveSiswa()
                    navigateBack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.isEntryValid
        ) {
            Text(text = stringResource(R.string.simpan))
        }
    }
}

@Composable
fun EntrySiswaBody(
    uiStateSiswa: UIStateSiswa,
    onValueChange: (DetailSiswa) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SiswaInputForm(
            detailSiswa = uiStateSiswa.detailSiswa,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            enabled = uiStateSiswa.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun SiswaInputForm(
    detailSiswa: DetailSiswa,
    onValueChange: (DetailSiswa) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = detailSiswa.nama,
            onValueChange = { onValueChange(detailSiswa.copy(nama = it)) },
            label = { Text("Nama") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = detailSiswa.alamat,
            onValueChange = { onValueChange(detailSiswa.copy(alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = detailSiswa.telpon,
            onValueChange = { onValueChange(detailSiswa.copy(telpon = it)) },
            label = { Text("Telepon") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}