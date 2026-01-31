package com.example.perpus.view.uicontroller

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perpus.view.route.DestinasiBuku
import com.example.perpus.view.route.DestinasiEditBuku
import com.example.perpus.view.route.DestinasiHome
import com.example.perpus.view.route.DestinasiPeminjaman
import com.example.perpus.view.route.DestinasiPengembalian
import com.example.perpus.view.route.DestinasiTambahBuku
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import com.example.perpus.viewmodel.TransaksiViewModel
import com.example.perpus.viewmodel.provider.PenyediaViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AplikasiPerpustakaan(
	navController: NavHostController = rememberNavController(),
	modifier: Modifier = Modifier
) {
	NavHost(
		navController = navController,
		startDestination = DestinasiHome.route,
		modifier = modifier
	) {

		// ================= HOME =================
		composable(DestinasiHome.route) {
			HalamanHome(
				onKelolaBukuClick = {
					navController.navigate(DestinasiBuku.route)
				},
				onPeminjamanClick = {
					navController.navigate(DestinasiPeminjaman.route)
				},
				onPengembalianClick = { navController.navigate(DestinasiPengembalian.route) }

			)
		}

		// ================= BUKU =================
		composable(DestinasiBuku.route) {
			HalamanBuku(
				navigateToFormBuku = {
					navController.navigate(DestinasiTambahBuku.route)
				},
				navigateToEditBuku = { idBuku ->
					navController.navigate("${DestinasiEditBuku.route}/$idBuku")
				},
				navigateBack = {
					navController.popBackStack()
				}
			)
		}


		composable(DestinasiPeminjaman.route) {
			HalamanPeminjaman(
				navigateToFormPeminjaman = {
					navController.navigate("form_peminjaman")
				},
				navigateToEditPeminjaman = { id ->
					navController.navigate("form_peminjaman/edit/$id")
				},
				onNavigateUp = {
					navController.navigateUp()
				}
			)
		}

		composable("form_peminjaman") {
			val viewModel: TransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)

			HalamanFormPeminjaman(
				onSimpan = { nama, idBuku, tanggal, judulBuku ->
					val idBukuInt = idBuku.toIntOrNull()
					if (idBukuInt != null && idBukuInt > 0) {
						viewModel.tambahPeminjaman(nama, idBukuInt, tanggal, judulBuku)
						navController.navigateUp()
					}
				},
				onNavigateUp = { navController.navigateUp() }
			)
		}

		composable(DestinasiPengembalian.route) {
			HalamanPengembalian(
				navigateBack = { navController.navigateUp() }
			)
		}



		composable(DestinasiTambahBuku.route) {
			TambahBukuScreen(

				canNavigateBack = true,
				navigateBack = {
					navController.popBackStack()
				}
			)
		}

		// ================= EDIT BUKU =================
		composable(
			route = "${DestinasiEditBuku.route}/{bukuId}", // Pastikan ada /{bukuId}
			arguments = listOf(navArgument("bukuId") { type = NavType.IntType })
		) {
			HalamanEditBuku(
				navigateBack = { navController.popBackStack() },
				onNavigateUp = { navController.navigateUp() }
			)
		}
		composable("form_peminjaman/edit/{id}") { backStackEntry ->
			val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
			if (id != -1) {
				FormPeminjamanScreen(
					editMode = true,
					transaksiId = id,
					onNavigateUp = { navController.navigateUp() }
				)
			}
		}
	}
}
