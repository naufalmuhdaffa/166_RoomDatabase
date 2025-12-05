package com.example.myarsitekturmvvm.view


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myarsitekturmvvm.R
import com.example.myarsitekturmvvm.room.Siswa
import com.example.myarsitekturmvvm.view.route.DestinasiHome
import com.example.myarsitekturmvvm.viewmodel.provider.PenyediaViewModel
import com.example.myarsitekturmvvm.viewmodel.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate:(Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    val uiState = viewModel.homeUiState.collectAsState().value

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(id = DestinasiHome.titleRes),
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToItemEntry) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.tambah_siswa)
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->

        if (uiState.listSiswa.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.tidak_ada_data))
            }
        } else

        {
//            LazyColumn(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(uiState.listSiswa) { siswa ->
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 16.dp)
//                    ) {
//                        Column(
//                            modifier = Modifier.padding(16.dp)
//                        ) {
//                            Text(text = siswa.nama, style = MaterialTheme.typography.titleMedium)
//                            Text(text = siswa.alamat)
//                            Text(text = siswa.telpon)
//                        }
//                    }
//                }
//            }
            val uiStateSiswa by viewModel.homeUiState.collectAsState()
            BodyHome(
                itemSiswa = uiStateSiswa.listSiswa,
                onSiswaClick = navigateToItemUpdate,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }


    }
}


@Composable
fun BodyHome(
    itemSiswa: List<Siswa>,
    onSiswaClick: (Int) -> Unit,
    modifier: Modifier=Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        if(itemSiswa.isEmpty()){
            Text(
                text = stringResource(R.string.deskripsi_no_item),
                textAlign = TextAlign.Center,
                style =  MaterialTheme.typography.titleMedium
            )
        }else{
            ListSiswa(
                itemSiswa = itemSiswa,

                onSiswaClick = {onSiswaClick(it.id)},
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_extra_large))
            )
        }
    }
}



@Composable
fun ListSiswa (
    itemSiswa : List<Siswa>,
    onSiswaClick: (Siswa) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn (modifier = modifier) { // Gunakan modifier yang diteruskan
        items(
            items = itemSiswa,
            key = { it.id }
        ) { person ->
            DetailDataSiswa(
                siswa = person,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onSiswaClick(person) }
            )
        }
    }
}