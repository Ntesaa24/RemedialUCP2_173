package com.example.perpus.view.uicontroller

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentReturn
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanHome(
	onKelolaBukuClick: () -> Unit,
	onPeminjamanClick: () -> Unit,
	onPengembalianClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Surface(
		modifier = Modifier.fillMaxSize(),
		color = Color(0xFF1C1B1F)
	) {
		Scaffold(
			containerColor = Color.Transparent,
			topBar = {
				BukuTopAppBar(
					title = "Perpustakaan Digital",
					canNavigateBack = false,
					isDark = true
				)
			}
		) { innerPadding ->
			Column(
				modifier = modifier
					.padding(innerPadding)
					.fillMaxSize()
					.padding(24.dp),
				horizontalAlignment = Alignment.Start
			) {
				Text(
					text = "Selamat Datang di\nMenu Utama",
					style = MaterialTheme.typography.headlineMedium,
					fontWeight = FontWeight.Bold,
					color = Color.White,
					lineHeight = 32.sp,
					modifier = Modifier.padding(bottom = 32.dp)
				)

				LazyColumn(
					verticalArrangement = Arrangement.spacedBy(16.dp),
					modifier = Modifier.fillMaxWidth()
				) {
					item {
						MenuCard(
							title = "Kelola Buku",
							subtitle = "Daftar, Tambah, dan Edit Buku",
							icon = Icons.Default.Book,
							containerColor = Color(0xFF7CB9E8),
							onClick = onKelolaBukuClick
						)
					}
					item {
						MenuCard(
							title = "Peminjaman Buku",
							subtitle = "Catat transaksi peminjaman baru",
							icon = Icons.Default.LibraryBooks,
							containerColor = Color(0xFF77DD77),
							onClick = onPeminjamanClick
						)
					}
					item {
						MenuCard(
							title = "Pengembalian Buku",
							subtitle = "Proses buku yang telah kembali",
							icon = Icons.Default.AssignmentReturn,
							containerColor = Color(0xFFFFB347),
							onClick = onPengembalianClick
						)
					}
				}
			}
		}
	}
}

@Composable
fun MenuCard(
	title: String,
	subtitle: String,
	icon: ImageVector,
	containerColor: Color,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick() },
		shape = RoundedCornerShape(16.dp),
		colors = CardDefaults.cardColors(containerColor = containerColor),
		elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
	) {
		Row(
			modifier = Modifier
				.padding(16.dp)
				.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically
		) {
			Box(
				modifier = Modifier
					.size(56.dp)
					.background(Color.White.copy(alpha = 0.3f), CircleShape),
				contentAlignment = Alignment.Center
			) {
				Icon(
					imageVector = icon,
					contentDescription = null,
					modifier = Modifier.size(28.dp),
					tint = Color.White
				)
			}

			Spacer(modifier = Modifier.width(16.dp))

			Column {
				Text(
					text = title,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					color = Color.White
				)
				Text(
					text = subtitle,
					style = MaterialTheme.typography.bodySmall,
					color = Color.White.copy(alpha = 0.8f)
				)
			}
		}
	}
}
