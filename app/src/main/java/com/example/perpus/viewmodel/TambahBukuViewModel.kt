package com.example.perpus.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.perpus.Repository.RepositoryBuku

class TambahBukuViewModel( private val repositoryBuku: RepositoryBuku) : ViewModel() {

	var uiStateBuku by mutableStateOf(BukuUIState())
		private set

	private fun validasiInput(uiState : DetailBuku = uiStateBuku.detailBuku) : Boolean {
		return with(uiState) {
			judul.isNotBlank() && penulis.isNotBlank() && penulis.isNotBlank() && kategori.isNotBlank() && stok.isNotBlank()
		}
	}

	fun updateUiState(detailBuku: DetailBuku) {
		uiStateBuku = BukuUIState(detailBuku = detailBuku, isEntryValid = validasiInput(detailBuku))
	}

	suspend fun saveSiswa() {
		if(validasiInput()) {
			repositoryBuku.insertBuku(uiStateBuku.detailBuku.toBuku())
		}
	}
}
