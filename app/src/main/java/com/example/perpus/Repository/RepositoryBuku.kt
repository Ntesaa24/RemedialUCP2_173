package com.example.perpus.Repository

import com.example.perpus.room.Buku
import kotlinx.coroutines.flow.Flow

interface  RepositoryBuku{
	fun getAllBuku() : Flow<List<Buku>>
	suspend fun insertBuku(buku: Buku)
	suspend fun updateBuku(buku: Buku)
	suspend fun deleteBuku(buku: Buku)
	suspend fun updateStok(id: Int, stok: Int)
	suspend fun getStok(id: Int) : Int
	fun getBukuStream(bukuId: Int) : Flow<Buku?>

	suspend fun getAllBukuStatic(): List<Buku>

}
