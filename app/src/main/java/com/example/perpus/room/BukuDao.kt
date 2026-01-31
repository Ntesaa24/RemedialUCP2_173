package com.example.perpus.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BukuDao {
	@Query("SELECT * FROM buku ORDER BY judul")
	fun getAllBuku(): Flow<List<Buku>>

	@Insert
	suspend fun insertBuku(buku: Buku)

	@Update
	suspend fun updateBuku(buku: Buku)

	@Delete
	suspend fun deleteBuku(buku: Buku)

	@Query("SELECT * FROM buku WHERE idBuku = :id")
	fun getBukuById(id: Int): Flow<Buku?>

	@Query("UPDATE buku SET stok = :stok WHERE idBuku = :id")
	suspend fun updateStok(id: Int, stok: Int)

	@Query("SELECT stok FROM buku WHERE idBuku = :id")
	suspend fun getStok(id: Int): Int

	@Query("SELECT COUNT(*) FROM transaksi WHERE idBuku = :id AND status = 'aktif'")
	suspend fun getJumlahPinjamAktif(id: Int): Int

	@Query("SELECT * FROM buku ORDER BY judul")
	suspend fun getAllBukuStatic(): List<Buku>

}
