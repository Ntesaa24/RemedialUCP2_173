package com.example.perpus.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TransaksiDao {
	@Insert
	suspend fun insertTransaksi(transaksi: Transaksi): Long

	@Query("SELECT * FROM transaksi WHERE status = 'aktif'")
	suspend fun getTransaksiAktif(): List<Transaksi>

	@Query("UPDATE transaksi SET tanggalKembali = :tgl, status = 'selesai' WHERE id = :id")
	suspend fun selesaikanPengembalian(id: Int, tgl: String)

	@Query("SELECT * FROM transaksi WHERE idBuku = :idBuku AND status = 'aktif'")
	suspend fun getTransaksiAktifByIdBuku(idBuku: Int): List<Transaksi>

	@Query("""
    SELECT 
        t.id AS id,
        t.namaAnggota AS namaAnggota,
        t.idBuku AS idBuku,
        b.judul AS judulBuku,
        t.tanggalPinjam AS tanggalPinjam,
        t.status AS status
    FROM transaksi t
    JOIN buku b ON t.idBuku = b.idBuku
    WHERE t.status = 'aktif'
""")
	suspend fun getTransaksiAktifDenganJudul(): List<Transaksi>

	@Query("DELETE FROM transaksi WHERE id = :id")
	suspend fun hapusTransaksi(id: Int)

	// Tambahkan ini di dalam interface TransaksiDao

	@Update
	suspend fun updateTransaksi(transaksi: Transaksi)

	@Query("SELECT * FROM transaksi WHERE id = :id")
	suspend fun getTransaksiById(id: Int): Transaksi?



}
