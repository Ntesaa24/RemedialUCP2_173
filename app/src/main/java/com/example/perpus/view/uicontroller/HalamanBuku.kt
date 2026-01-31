package com.example.perpus.view.uicontroller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.perpus.room.Buku
import com.example.perpus.viewmodel.HomeViewModel
import com.example.perpus.viewmodel.providerl.BukuTopAppBar
import com.example.perpus.viewmodel.provider.PenyediaViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanBuku(
	navigateBack : () -> Unit,
	navigateToFormBuku: () -> Unit,
	navigateToEditBuku: (Int) -> Unit,
	onDeleteClick: (Buku) -> Unit = {},
	modifier: Modifier = Modifier,
	viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
	val homeUiState by viewModel.homeUiState.collectAsState()
	var searchQuery by remember { mutableStateOf("") }



	Scaffold(
		topBar = {
			BukuTopAppBar(title = "Kelola Buku", canNavigateBack = true, navigateUp = navigateBack)
		},
		floatingActionButton = {
			ExtendedFloatingActionButton(
				onClick = navigateToFormBuku,
				containerColor = Color(0xFF4A69FF),
				contentColor = Color.White,
				shape = RoundedCornerShape(12.dp),
				modifier = Modifier.fillMaxWidth(0.9f).padding(bottom = 16.dp)
			) {
				Icon(Icons.Default.Add, contentDescription = null)
				Spacer(Modifier.width(8.dp))
				Text("Tambah Buku", fontWeight = FontWeight.Bold)
			}
		},
		floatingActionButtonPosition = FabPosition.Center
	) { innerPadding ->
		Column(
			modifier = modifier
				.padding(innerPadding)
				.fillMaxSize()
				.background(Color(0xFFF8F9FA))
				.padding(horizontal = 16.dp)
		) {
			Spacer(modifier = Modifier.height(16.dp))

			// 1. Search Bar
			OutlinedTextField(
				value = searchQuery,
				onValueChange = { searchQuery = it },
				placeholder = { Text("Cari buku...") },
				leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
				modifier = Modifier.fillMaxWidth(),
				shape = RoundedCornerShape(12.dp),
				colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White)
			)

			Spacer(modifier = Modifier.height(20.dp))

			val filteredList = homeUiState.listBuku.filter {
				it.judul.contains(searchQuery, ignoreCase = true)
			}

			LazyColumn(
				verticalArrangement = Arrangement.spacedBy(12.dp),
				contentPadding = PaddingValues(bottom = 100.dp)
			) {
				items(filteredList) { buku ->
					ItemBukuCard(
						buku = buku,
						onEdit = {
							navigateToEditBuku(buku.idBuku) },
						onDelete = { viewModel.deleteBuku(buku) }
					)
				}
			}
		}
	}
}

@Composable
fun ItemBukuCard(
	buku: Buku,
	onEdit: (Int) -> Unit,
	onDelete: () -> Unit,
	modifier: Modifier = Modifier
) {
	var showDeleteDialog by remember { mutableStateOf(false) }


	Card(
		modifier = Modifier.fillMaxWidth(),
		colors = CardDefaults.cardColors(containerColor = Color.White),
		elevation = CardDefaults.cardElevation(2.dp),
		shape = RoundedCornerShape(12.dp)
	) {
		Row(
			modifier = Modifier.padding(12.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Surface(
				modifier = Modifier.size(80.dp),
				shape = RoundedCornerShape(8.dp),
				color = Color(0xFFE0E0E0)
			) {
				AsyncImage(
					model = File(buku.coverUrl),
					contentDescription = "Cover Buku",
					contentScale = ContentScale.Crop,
					modifier = Modifier.fillMaxSize()
				)
			}

			Spacer(modifier = Modifier.width(16.dp))

			Column(modifier = Modifier.weight(1f)) {
				Text(
					text = buku.judul,
					fontWeight = FontWeight.Bold,
					fontSize = 16.sp,
					color = Color.Black
				)
				Text(
					text = "Stok: ${buku.stok}",
					fontSize = 14.sp,
					color = Color.Gray
				)

				Spacer(modifier = Modifier.height(8.dp))

				Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
					// Tombol Edit
					Button(
						onClick = {onEdit(buku.idBuku)},
						colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A69FF)),
						shape = RoundedCornerShape(8.dp),
						modifier = Modifier.height(36.dp),
						contentPadding = PaddingValues(horizontal = 16.dp)
					) {
						Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
						Spacer(Modifier.width(4.dp))
						Text("Edit", fontSize = 12.sp)
					}

					// Tombol Hapus
					Button(
						onClick = { showDeleteDialog = true },
						colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
						shape = RoundedCornerShape(8.dp),
						modifier = Modifier.height(36.dp),
						contentPadding = PaddingValues(horizontal = 16.dp)
					) {
						Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
						Spacer(Modifier.width(4.dp))
						Text("Hapus", fontSize = 12.sp)
					}
				}
			}
		}
	}
	// Dialog Konfirmasi Hapus
	if (showDeleteDialog) {
		AlertDialog(
			onDismissRequest = { showDeleteDialog = false },
			title = { Text("Konfirmasi Hapus") },
			text = { Text("Yakin ingin menghapus buku \"${buku.judul}\"?") },
			confirmButton = {
				TextButton(
					onClick = {
						onDelete()
						showDeleteDialog = false
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



