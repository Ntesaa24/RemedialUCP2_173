package com.example.perpus.view.uicontroller

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpus.view.route.DestinasiEditBuku
import com.example.perpus.viewmodel.EditBukuViewModel
import com.example.perpus.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEditBuku(
	navigateBack: () -> Unit,
	onNavigateUp: () -> Unit,
	modifier: Modifier = Modifier,
	viewModel: EditBukuViewModel = viewModel (factory = PenyediaViewModel.Factory)
) {
	val coroutineScope = rememberCoroutineScope ()

	Scaffold (
		topBar = {
			BukuTopAppBar(
				title = stringResource(DestinasiEditBuku.titleRes),
				canNavigateBack = true,
				navigateUp = onNavigateUp
			)
		},
		modifier = modifier
	){ innerPadding ->
		EntryBukuBody(
			uiStateBuku = viewModel.uiStateBuku,
			onValueChange = viewModel::updateUiState,
			onSaveClick = {
				if (viewModel.uiStateBuku.isEntryValid)
					coroutineScope.launch {
						viewModel.updateBuku()
						navigateBack()
					}
			},
			modifier = Modifier.padding(innerPadding),
			buttonText = "Update Data"
		)
	}
}
