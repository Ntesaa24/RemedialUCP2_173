package com.example.perpus.view.uicontroller

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpus.R
import com.example.perpus.room.Transaksi
import com.example.perpus.view.route.DestinasiPeminjaman
import com.example.perpus.viewmodel.provider.PenyediaViewModel
import com.example.perpus.viewmodel.TransaksiViewModel
import androidx.compose.runtime.setValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPeminjaman(
	navigateToFormPeminjaman: () -> Unit,
	navigateToEditPeminjaman: (Int) -> Unit,
	onNavigateUp: () -> Unit,
	modifier: Modifier = Modifier,
	viewModel: TransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
	val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
	Scaffold(
		modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			PeminjamanTopAppBar(
				title = stringResource(DestinasiPeminjaman.titleRes),
				canNavigateBack = true,
				onNavigateUp = onNavigateUp, // ← lewatkan ke sini
				scrollBehavior = scrollBehavior
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = navigateToFormPeminjaman,
				shape = MaterialTheme.shapes.medium,
				modifier = Modifier.padding(16.dp)
			) {
				Icon(
					imageVector = Icons.Default.Add,
					contentDescription = stringResource(R.string.catat_peminjaman)
				)
			}
		}
	) { innerPadding ->
		val uiState by viewModel.transaksiAktif.collectAsState()
		BodyPeminjaman(
			transaksiAktif = uiState,
			onDeleteTransaksi = { id ->
				viewModel.hapusTransaksi(id)
			},
			onEditTransaksi = { id ->
				navigateToEditPeminjaman(id)
			},
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		)
	}
}

@Composable
fun BodyPeminjaman(
	transaksiAktif: List<Transaksi>,
	onDeleteTransaksi: (Int) -> Unit,
	onEditTransaksi: (Int) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = modifier
	) {
		if (transaksiAktif.isEmpty()) {
			Text(
				text = stringResource(R.string.tidak_ada_peminjaman),
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.titleLarge
			)
		} else {
			ListPeminjaman(
				transaksiAktif = transaksiAktif,
				onDeleteTransaksi = onDeleteTransaksi,
				onEditTransaksi = onEditTransaksi,
				modifier = Modifier.padding(horizontal = 8.dp)
			)
		}
	}
}

@Composable
fun ListPeminjaman(
	transaksiAktif: List<Transaksi>,
	onDeleteTransaksi: (Int) -> Unit,
	onEditTransaksi: (Int) -> Unit,
	modifier: Modifier = Modifier
) {
	LazyColumn(modifier = Modifier) {
		items(transaksiAktif) { transaksi ->
			var showDeleteDialog by remember { mutableStateOf(false) }

			Card(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp),
				elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
			) {
				Column(
					modifier = Modifier.padding(16.dp),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					Text(
						text = "Anggota: ${transaksi.namaAnggota}",
						style = MaterialTheme.typography.titleMedium
					)
					Text(
						text = "ID Buku: ${transaksi.idBuku}",
						style = MaterialTheme.typography.bodyMedium
					)
					Text(
						text = "Judul: ${transaksi.judulBuku}",
						style = MaterialTheme.typography.bodyMedium
					)
					Text(
						text = "Tanggal Pinjam: ${transaksi.tanggalPinjam}",
						style = MaterialTheme.typography.bodyMedium
					)
					Text(
						text = "Status: ${stringResource(if (transaksi.status == "aktif") R.string.aktif else R.string.selesai)}",
						style = MaterialTheme.typography.bodyMedium,
						color = if (transaksi.status == "aktif") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
					)
					Spacer(modifier = Modifier.height(12.dp))

					// Tombol Hapus

					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.End
					){
						Button(
							onClick = { onEditTransaksi(transaksi.id) },
							shape = RoundedCornerShape(8.dp),
							colors = ButtonDefaults.buttonColors(
								containerColor = MaterialTheme.colorScheme.primary,
								contentColor = MaterialTheme.colorScheme.onPrimary
							),
							modifier = Modifier.padding(end = 8.dp)
						) {
							Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
							Spacer(Modifier.width(4.dp))
							Text("Edit", fontSize = 12.sp)
						}
					}
					Button(
						onClick = { showDeleteDialog = true },
						colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
						shape = RoundedCornerShape(8.dp),
						modifier = Modifier.align(Alignment.End)
					) {
						Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
						Spacer(Modifier.width(4.dp))
						Text("Hapus", fontSize = 12.sp)
					}

				}
			}


			if (showDeleteDialog) {
				AlertDialog(
					onDismissRequest = { showDeleteDialog = false },
					title = { Text("Konfirmasi Hapus") },
					text = { Text("Yakin ingin menghapus peminjaman oleh ${transaksi.namaAnggota}?") },
					confirmButton = {
						TextButton(
							onClick = {
								showDeleteDialog = false
								onDeleteTransaksi(transaksi.id)
							}
						) {
							Text("Hapus", color = MaterialTheme.colorScheme.error)
						}
					},
					dismissButton = {
						TextButton(onClick = { showDeleteDialog = false }) {
							Text("Batal")
						}
					}
				)
			}
		}
	}
}
