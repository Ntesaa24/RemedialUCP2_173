package com.example.perpus.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buku")
data class Buku(
	@PrimaryKey(autoGenerate = true) val idBuku: Int = 0,
	val judul: String,
	val coverUrl : String,
	val penulis: String,
	val kategori: String? = null,
	val stok: Int
)
