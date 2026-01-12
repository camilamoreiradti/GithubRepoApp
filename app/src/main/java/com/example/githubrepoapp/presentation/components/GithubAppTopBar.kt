package com.example.githubrepoapp.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GithubAppTopBar(
    navigateBack: () -> Unit,
    navigateBackText: String
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            TextButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Details",
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(navigateBackText)
            }
        }
    )
}