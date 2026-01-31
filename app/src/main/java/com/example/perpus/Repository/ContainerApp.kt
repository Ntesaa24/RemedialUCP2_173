package com.example.perpus.Repository

import android.app.Application
import android.content.Context
import com.example.perpus.room.DatabasePerpustakaan

interface ContainerApp {
	val repositoryTransaksi: RepositoryTransaksi
	val repositoryBuku: RepositoryBuku
}

class ContainerDataApp (private val context: Context): ContainerApp {
	override val repositoryBuku: RepositoryBuku by lazy {
		OffLinedRepositoryBuku(
			DatabasePerpustakaan.getDatabase(context).bukuDao())
	}
	override val repositoryTransaksi: RepositoryTransaksi by lazy {
		RepositoryTransaksi(
			DatabasePerpustakaan.getDatabase(context).transaksiDao()
		)
	}
}




class AplikasiPerpustakaan : Application() {
	lateinit var container : ContainerApp

	override fun onCreate() {
		super.onCreate()
		container = ContainerDataApp(this)
	}
}

