package com.example.perpus.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
	entities = [Buku::class, Transaksi::class],
	version = 1,
	exportSchema = false
)
abstract class DatabasePerpustakaan : RoomDatabase() {
	abstract fun bukuDao(): BukuDao
	abstract fun transaksiDao(): TransaksiDao

	companion object {
		@Volatile
		private var INSTANCE: DatabasePerpustakaan? = null

		fun getDatabase(context: Context): DatabasePerpustakaan {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					DatabasePerpustakaan::class.java,
					"perpustakaan.db"
				).build()
				INSTANCE = instance
				instance
			}
		}
	}
}
