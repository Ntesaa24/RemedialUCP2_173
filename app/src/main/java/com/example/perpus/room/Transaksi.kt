package com.example.perpus.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaksi")
data class Transaksi(
	@PrimaryKey(autoGenerate = true) val id: Int = 0,
	val idBuku: Int,
	val judulBuku : String,
	val namaAnggota: String,
	val tanggalPinjam: String, // "YYYY-MM-DD"
	val tanggalKembali: String? = null,
	val status: String // "aktif" / "selesai"
)
