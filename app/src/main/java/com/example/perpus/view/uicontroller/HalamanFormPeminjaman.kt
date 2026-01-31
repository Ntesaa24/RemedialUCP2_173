package com.example.perpus.view.uicontroller

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanFormPeminjaman(
	onSimpan: (namaAnggota: String, idBuku: String, tanggal : LocalDateTime , judulBuku: String) -> Unit,
	onNavigateUp: () -> Unit
) {
	var namaAnggota by remember { mutableStateOf("") }
	var idBuku by remember { mutableStateOf("") }
	var judulBuku by remember { mutableStateOf("") }

	val isFormValid = namaAnggota.isNotBlank() &&
		idBuku.isNotBlank() &&
		judulBuku.isNotBlank()

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Form Peminjaman") },
				navigationIcon = {
					IconButton(onClick = onNavigateUp) {
						Icon(
							imageVector = Icons.Default.ArrowBack,
							contentDescription = "Kembali"
						)
					}
				}
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					if (isFormValid) {
						onSimpan(namaAnggota, idBuku, LocalDateTime.now() ,judulBuku)
					}
				},
				containerColor = if (isFormValid) MaterialTheme.colorScheme.primary
				else MaterialTheme.colorScheme.surfaceVariant
			) {
				Icon(
					imageVector = Icons.Default.Done,
					contentDescription = "Simpan"
				)
			}
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
				.padding(horizontal = 16.dp, vertical = 24.dp),
			verticalArrangement = Arrangement.spacedBy(24.dp)
		) {
			OutlinedTextField(
				value = namaAnggota,
				onValueChange = { namaAnggota = it },
				label = { Text("Nama Anggota") },
				modifier = Modifier.fillMaxWidth()
			)

			OutlinedTextField(
				value = idBuku,
				onValueChange = { idBuku = it },
				label = { Text("ID Buku") },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				modifier = Modifier.fillMaxWidth()
			)

			OutlinedTextField(
				value = judulBuku,
				onValueChange = { judulBuku = it },
				label = { Text("Judul Buku") },
				modifier = Modifier.fillMaxWidth()
			)

			if (!isFormValid &&
				(namaAnggota.isNotEmpty() || idBuku.isNotEmpty() || judulBuku.isNotEmpty())) {
				Text(
					text = "Harap isi semua kolom",
					color = MaterialTheme.colorScheme.error
				)
			}
		}
	}
}
