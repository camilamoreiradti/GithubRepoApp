package com.example.githubrepoapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubrepoapp.domain.remote.model.repo1
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme

@Composable
fun InfoSection(label: String, content: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleMedium,
    )

    Text(
        text = content,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Preview(showBackground = true)
@Composable
fun previewInfoSection() {
    GithubRepoAppTheme {
        InfoSection(
            label = "Description",
            content = repo1.description
        )
    }
}