package com.example.githubrepoapp.presentation.repolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.githubrepoapp.domain.remote.repositories.model.RepoItem
import com.example.githubrepoapp.domain.remote.repositories.model.repo1
import com.example.githubrepoapp.domain.remote.repositories.model.repo2
import com.example.githubrepoapp.domain.remote.repositories.model.repo3
import com.example.githubrepoapp.presentation.baseviewmodel.State
import com.example.githubrepoapp.presentation.components.LoadingIndicator
import com.example.githubrepoapp.presentation.components.RepoListItem
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme

@Composable
fun RepoListScreen(
    toSignIn: () -> Unit,
    toAccount: () -> Unit,
    onNavigateToRepoItem: (ownerName: String, repoName: String) -> Unit,
) {
    val viewModel: RepoListViewModel = hiltViewModel()

    val uiState by viewModel.stateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize(toSignIn)
        viewModel.getRepoList()
    }

    when (val state = uiState) {
        State.Error -> {}

        State.Loading -> {
            LoadingIndicator()
        }

        is State.Success<*> -> {
            RepoListContent(
                toAccount = toAccount,
                repos = state.data as List<RepoItem>,
                onNavigateToRepoItem = { repoItem ->
                    onNavigateToRepoItem(repoItem.owner.name, repoItem.name)
                    viewModel.onItemClick(repoItem)
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListContent(
    toAccount: () -> Unit,
    repos: List<RepoItem>,
    onNavigateToRepoItem: (repoItem: RepoItem) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "email.com",
                    )
                },
                actions = {
                    IconButton(
                        onClick = toAccount,
                    ) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "account icon",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                },
                windowInsets = WindowInsets(left = 0.dp, right = 0.dp),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Repositories",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
            LazyColumn {
                itemsIndexed(repos) { _, item ->
                    RepoListItem(
                        repo = item,
                        onItemClicked = {
                            onNavigateToRepoItem(item)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewListScreen() {
    GithubRepoAppTheme {
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
            toAccount = { },
            onNavigateToRepoItem = { },
        )
    }
}