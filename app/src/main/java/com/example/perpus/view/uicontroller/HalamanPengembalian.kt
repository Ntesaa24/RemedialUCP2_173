package com.example.perpus.view.uicontroller

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.perpus.R
import com.example.perpus.room.Transaksi
import com.example.perpus.view.route.DestinasiPengembalian
import com.example.perpus.viewmodel.TransaksiViewModel
import androidx.compose.runtime.collectAsState
import com.example.perpus.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPengembalian(
	navigateBack: () -> Unit = {},
	modifier: Modifier = Modifier,
	viewModel: TransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

	val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
	Scaffold(
		modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			PengembalianTopAppBar(
				title = stringResource(DestinasiPengembalian.titleRes),
				canNavigateBack = true,
				onNavigateUp = navigateBack,
				scrollBehavior = scrollBehavior
			)
		}
	) { innerPadding ->
		val uiState by viewModel.transaksiAktif.collectAsState()
		BodyPengembalian(
			transaksiAktif = uiState,
			onKembalikan = { id -> viewModel.kembalikanBuku(id) {} },
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		)
	}
}

@Composable
fun BodyPengembalian(
	transaksiAktif: List<Transaksi>,
	onKembalikan: (Int) -> Unit,
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
			ListPengembalian(
				transaksiAktif = transaksiAktif,
				onKembalikan = onKembalikan,
				modifier = Modifier.padding(horizontal = 8.dp)
			)
		}
	}
}


@Composable
fun ListPengembalian(
	transaksiAktif: List<Transaksi>,
	onKembalikan: (Int) -> Unit,
	modifier: Modifier = Modifier
) {
	LazyColumn(modifier = Modifier) {
		items(transaksiAktif) { transaksi ->
			Card(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp),
				elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
			) {
				Column(
					modifier = Modifier.padding(16.dp),
					verticalArrangement = Arrangement.spacedBy(12.dp)
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
						text = "Judul Buku: ${transaksi.judulBuku}",
						style = MaterialTheme.typography.bodyMedium
					)
					Text(
						text = "Tanggal Pinjam: ${transaksi.tanggalPinjam}",
						style = MaterialTheme.typography.bodyMedium
					)
					Button(
						onClick = { onKembalikan(transaksi.id) },
						modifier = Modifier.fillMaxWidth(),
						shape = MaterialTheme.shapes.small
					) {
						Text(stringResource(R.string.kembalikan))
					}
				}
			}
		}
	}
}
