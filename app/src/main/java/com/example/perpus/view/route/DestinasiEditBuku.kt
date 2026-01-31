package com.example.perpus.view.route

import com.example.perpus.R

object DestinasiEditBuku : DestinasiNavigasi {
	// Gunakan 'override' karena ini adalah properti dari interface DestinasiNavigasi
	override val route = "edit_buku"

	// Hubungkan dengan ID string yang ada di strings.xml Anda
	override val titleRes = R.string.edit_buku

	const val itemIdArg = "idBuku"
	val routeWithArgs = "$route/{$itemIdArg}"
}
