package com.example.perpus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.perpus.repository.RepositoryBuku
import com.example.perpus.room.Buku

data class BukuUIState(
	val detailBuku: DetailBuku = DetailBuku(),
	val isEntryValid: Boolean = false
)

class BukuViewModel(private val repositoryBuku: RepositoryBuku) : ViewModel() {
	var uiStateBuku by	 mutableStateOf(BukuUIState())
		private set

	fun updateUiState(detailBuku: DetailBuku) {
		uiStateBuku = BukuUIState(
			detailBuku = detailBuku,
			isEntryValid = validasiInput(detailBuku)
		)
	}

	private fun validasiInput(uiState: DetailBuku = uiStateBuku.detailBuku): Boolean {
		return with(uiState) {
			judul.isNotBlank() && penulis.isNotBlank() && kategori.isNotBlank() && stok.isNotBlank()
		}
	}

	suspend fun saveBuku() {
		if (validasiInput()) {
			repositoryBuku.insertBuku(uiStateBuku.detailBuku.toBuku())
		}
	}
	suspend fun updateBuku() {
		if (validasiInput()) {
			repositoryBuku.updateBuku(uiStateBuku.detailBuku.toBuku())
		}
	}

}

data class DetailBuku(
	val idBuku: Int = 0,
	val coverUrl : String = "",
	val judul: String = "",
	val penulis: String = "",
	val kategori: String = "",
	val stok: String = ""
)

fun DetailBuku.toBuku(): Buku = Buku(
	idBuku = idBuku,
	coverUrl = coverUrl,
	judul = judul,
	penulis = penulis,
	kategori = kategori,
	stok = stok.toIntOrNull() ?: 0
)

fun Buku.toUIStateBuku(isEntryValid: Boolean = false): BukuUIState = BukuUIState(
	detailBuku = DetailBuku(
		idBuku = idBuku,
		coverUrl = coverUrl,
		judul = judul,
		penulis = penulis,
		kategori = kategori ?: "",
		stok = stok.toString()
	),
	isEntryValid = isEntryValid

)
fun Buku.toUiStateBuku(isEntryValid: Boolean = false): BukuUIState = BukuUIState(
	detailBuku = DetailBuku(
		idBuku = this.idBuku,
		coverUrl = this.coverUrl,
		judul = this.judul,
		penulis = this.penulis,
		kategori = this.kategori ?: "",
		stok = this.stok.toString()
	),
	isEntryValid = isEntryValid
)
