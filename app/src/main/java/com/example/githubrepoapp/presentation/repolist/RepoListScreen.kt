package com.example.githubrepoapp.presentation.repolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubrepoapp.domain.remote.model.RepoItem
import com.example.githubrepoapp.domain.remote.model.repo1
import com.example.githubrepoapp.domain.remote.model.repo2
import com.example.githubrepoapp.domain.remote.model.repo3
import com.example.githubrepoapp.presentation.components.RepoListItem
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme

@Composable
fun RepoListScreen(
    onNavigateToRepoItem: (id: Long) -> Unit,
) {
    RepoListContent(
        repos = listOf<RepoItem>(
            repo1,
            repo2,
            repo3,
        ),
        onNavigateToRepoItem = { }
    )
}

@Composable
fun RepoListContent(
    repos: List<RepoItem>,
    onNavigateToRepoItem: (name: String) -> Unit
) {
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Repositories",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            LazyColumn(
            ) {
                itemsIndexed(repos) { index, item ->
                    RepoListItem(
                        repo = item,
                        onItemClicked = { onNavigateToRepoItem(item.name) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun previewListScreen() {
    GithubRepoAppTheme() {
        RepoListContent(
            repos = listOf(
                repo1,
                repo2,
                repo3,
                repo1,
                repo2,
                repo3,
                repo1,
                repo2,
                repo3,
                repo1,
                repo2,
                repo3,
                repo1,
                repo3,
                ),
            onNavigateToRepoItem = { }
        )
    }
}