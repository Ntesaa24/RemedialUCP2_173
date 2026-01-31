package com.example.perpus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perpus.repository.RepositoryBuku
import com.example.perpus.room.Buku
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeUiState(
	val listBuku: List<Buku> = listOf()
)

class HomeViewModel(private val repositoryBuku: RepositoryBuku) : ViewModel() {

	val homeUiState: StateFlow<HomeUiState> =
		repositoryBuku.getAllBuku()
			.map { HomeUiState(listBuku = it) }
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.WhileSubscribed(5000),
				initialValue = HomeUiState()
			)
	fun deleteBuku(buku: Buku) {
		viewModelScope.launch {
			repositoryBuku.deleteBuku(buku)
		}
	}
}

