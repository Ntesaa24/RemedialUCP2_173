package com.example.perpus.view.uicontroller

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BukuTopAppBar(
	title: String,
	canNavigateBack: Boolean,
	modifier: Modifier = Modifier,
	scrollBehavior: TopAppBarScrollBehavior? = null,
	navigateUp: () -> Unit = {},
	isDark : Boolean = false
) {
	CenterAlignedTopAppBar(
		title = { Text(title, color = if (isDark) Color.White else Color.Black) },
		modifier = modifier,
		colors = TopAppBarDefaults.topAppBarColors(containerColor = if (isDark) Color(0xFF1C1B1F) else Color.White),
		scrollBehavior = scrollBehavior,
		navigationIcon = {
			if (canNavigateBack) {
				IconButton(onClick = navigateUp) {
					Icon(
						imageVector = Icons.Filled.ArrowBack,
						contentDescription = "Kembali"
					)
				}
			}
		}
	)
}
