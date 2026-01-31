package com.example.perpus.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.perpus.Repository.RepositoryBuku

class EditBukuViewModel(
	savedStateHandle: SavedStateHandle,
	private val repositoryBuku: RepositoryBuku
) : ViewModel() {

	private val _bukuId: Int? = savedStateHandle["bukuId"]

	var uiStateBuku by mutableStateOf(BukuUIState())
		private set

	init {
		viewModelScope.launch {
			try {
				val id = _bukuId ?: throw IllegalArgumentException("ID Buku Null")

				repositoryBuku.getBukuStream(id)
					.filterNotNull()
					.first()
					.let { buku ->
						// Konversi ke UI State
						uiStateBuku = buku.toUiStateBuku(true)
					}
			} catch (e: Exception) {
				println("DEBUG: Error memuat data: ${e.message}")
				uiStateBuku = BukuUIState(isEntryValid = false)
			}
		}
	}

	private fun validasiInput(uiState: DetailBuku = uiStateBuku.detailBuku): Boolean {
		return with(uiState) {
			penulis.isNotBlank() && judul.isNotBlank() && kategori.isNotBlank() && stok.isNotBlank()
		}
	}

	fun updateUiState(detailBuku: DetailBuku) {
		uiStateBuku = BukuUIState(
			detailBuku = detailBuku,
			isEntryValid = validasiInput(detailBuku)
		)
	}

	suspend fun updateBuku() {
		if (validasiInput()) {
			try {
				repositoryBuku.updateBuku(uiStateBuku.detailBuku.toBuku())
				println("DEBUG: Update buku berhasil")

			} catch (e: Exception) {
				println("DEBUG: Error updating buku: ${e.message}")
			}
		}
	}
}
