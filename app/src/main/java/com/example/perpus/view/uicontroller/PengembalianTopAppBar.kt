package com.example.perpus.view.uicontroller

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengembalianTopAppBar(
	title: String,
	canNavigateBack: Boolean,
	onNavigateUp: () -> Unit,
	scrollBehavior: TopAppBarScrollBehavior,
	modifier: Modifier = Modifier
) {
	CenterAlignedTopAppBar(
		title = { Text(title) },
		navigationIcon = if (canNavigateBack) {
			{
				IconButton(onClick = onNavigateUp) {
					Icon(
						imageVector = Icons.Default.ArrowBack,
						contentDescription = "Kembali"
					)
				}
			}
		} else {
			{}
		},
		scrollBehavior = scrollBehavior,
		modifier = modifier
	)
}
