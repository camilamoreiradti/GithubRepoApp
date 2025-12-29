package com.example.githubrepoapp.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun InfoSection(label: String, content: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
    )

    Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium,
    )
}