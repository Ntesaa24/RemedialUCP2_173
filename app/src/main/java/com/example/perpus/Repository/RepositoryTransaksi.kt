package com.example.perpus.Repository

import com.example.perpus.room.Transaksi
import com.example.perpus.room.TransaksiDao

class RepositoryTransaksi(private val dao: TransaksiDao) {
	suspend fun insertTransaksi(transaksi: Transaksi) = dao.insertTransaksi(transaksi)
	suspend fun getTransaksiAktif() = dao.getTransaksiAktif()
	suspend fun selesaikanPengembalian(id: Int, tgl: String) = dao.selesaikanPengembalian(id, tgl)
	suspend fun getTransaksiAktifByIdBuku(idBuku: Int) = dao.getTransaksiAktifByIdBuku(idBuku)

	suspend fun hapusTransaksi(id: Int) {
		dao.hapusTransaksi(id)	}

	suspend fun updateTransaksi(transaksi: Transaksi) {
		dao.updateTransaksi(transaksi)
	}
	suspend fun getTransaksiById(id: Int): Transaksi? {
		return dao.getTransaksiById(id)
	}

}
