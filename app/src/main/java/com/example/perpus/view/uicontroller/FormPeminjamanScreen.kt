package com.example.perpus.view.uicontroller

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpus.R
import com.example.perpus.room.Transaksi
import com.example.perpus.viewmodel.provider.PenyediaViewModel
import com.example.perpus.viewmodel.TransaksiViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPeminjamanScreen(
	editMode: Boolean = false,
	transaksiId: Int = -1,
	viewModel: TransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory),
	onNavigateUp: () -> Unit
) {
	val snackbarHostState = remember { SnackbarHostState() }
	val scope = rememberCoroutineScope()

	var namaAnggota by remember { mutableStateOf("") }
	var idBukuText by remember { mutableStateOf("") }
	var judulBuku by remember { mutableStateOf("") }

	// Jika mode edit, ambil data transaksi
	LaunchedEffect(editMode, transaksiId) {
		if (editMode && transaksiId != -1) {
			val transaksi = viewModel.getTransaksiById(transaksiId)
			transaksi?.let {
				namaAnggota = it.namaAnggota
				idBukuText = it.idBuku.toString()
				judulBuku = it.judulBuku
			}
		}
	}

	Scaffold(
		topBar = {
			TopAppBar(
				title = { Text(stringResource(R.string.form_peminjaman)) },
				navigationIcon = {
					IconButton(onClick = onNavigateUp) {
						Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
					}
				}
			)
		},
		snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					if (namaAnggota.isBlank() || idBukuText.isBlank() || judulBuku.isBlank()) {
						scope.launch {
							snackbarHostState.showSnackbar("Semua field harus diisi!")
						}
						return@FloatingActionButton
					}

					val idBukuInt = idBukuText.toIntOrNull()
					if (idBukuInt == null || idBukuInt <= 0) {
						scope.launch {
							snackbarHostState.showSnackbar("ID Buku harus angka positif!")
						}
						return@FloatingActionButton
					}

					val transaksi = Transaksi(
						id = if (editMode) transaksiId else 0,
						namaAnggota = namaAnggota,
						idBuku = idBukuInt,
						judulBuku = judulBuku,
						tanggalPinjam = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()),
						status = "aktif"
					)

					if (editMode) {
						viewModel.updateTransaksi(transaksi)
					} else {
						viewModel.tambahTransaksi(transaksi)
					}

					onNavigateUp()
				},
				containerColor = MaterialTheme.colorScheme.primary,
				contentColor = MaterialTheme.colorScheme.onPrimary
			) {
				Icon(Icons.Default.Check, contentDescription = "Simpan")
			}
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
				.padding(horizontal = 16.dp, vertical = 12.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			OutlinedTextField(
				value = namaAnggota,
				onValueChange = { namaAnggota = it },
				label = { Text("Nama Anggota") },
				modifier = Modifier.fillMaxWidth()
			)

			OutlinedTextField(
				value = idBukuText,
				onValueChange = {
					if (it.all { char -> char.isDigit() }) {
						idBukuText = it
					}
				},
				label = { Text("ID Buku") },
				modifier = Modifier.fillMaxWidth(),
				keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
			)

			OutlinedTextField(
				value = judulBuku,
				onValueChange = { judulBuku = it },
				label = { Text("Judul Buku") },
				modifier = Modifier.fillMaxWidth()
			)

			Spacer(modifier = Modifier.height(64.dp))
		}
	}
}
