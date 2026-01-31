package com.example.perpus.view.uicontroller

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.perpus.viewmodel.BukuUIState
import com.example.perpus.viewmodel.DetailBuku

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBukuBody(
	uiStateBuku: BukuUIState,
	onValueChange: (DetailBuku) -> Unit,
	onSaveClick: () -> Unit,
	modifier: Modifier = Modifier,
	buttonText: String = "Simpan"
) {
	Column(
		modifier = modifier.fillMaxSize().padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		FormInputBuku(
			detailBuku = uiStateBuku.detailBuku,
			onValueChange = onValueChange,
			modifier = Modifier.fillMaxWidth()
		)

		Spacer(modifier = Modifier.height(16.dp))

		Button(
			onClick = onSaveClick,
			enabled = uiStateBuku.isEntryValid,
			modifier = Modifier.fillMaxWidth()
		) {
			Text(buttonText)
		}
	}
}
