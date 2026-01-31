package com.example.perpus.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.perpus.Repository.AplikasiPerpustakaan
import com.example.perpus.viewmodel.BukuViewModel
import com.example.perpus.viewmodel.EditBukuViewModel
import com.example.perpus.viewmodel.HomeViewModel
import com.example.perpus.viewmodel.TambahBukuViewModel
import com.example.perpus.viewmodel.TransaksiViewModel

object PenyediaViewModel {
	val Factory = viewModelFactory {
		initializer {
			BukuViewModel(
				aplikasiPerpustakaan().container.repositoryBuku
			)
		}
		initializer {
			HomeViewModel(aplikasiPerpustakaan().container.repositoryBuku)
		}
		initializer {
			TambahBukuViewModel(
				aplikasiPerpustakaan().container.repositoryBuku
			)
		}
		initializer {
			EditBukuViewModel(
				this.createSavedStateHandle(),
				repositoryBuku = aplikasiPerpustakaan().container.repositoryBuku)
		}
		initializer {
			TransaksiViewModel(
				aplikasiPerpustakaan().container.repositoryTransaksi,
				repositoryBuku = aplikasiPerpustakaan().container.repositoryBuku
			)

		}
	}
}

fun CreationExtras.aplikasiPerpustakaan(): AplikasiPerpustakaan =
	(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiPerpustakaan)


