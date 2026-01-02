package com.example.githubrepoapp.presentation.repolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
                onLogout = { viewModel.onLogoutClick() },
                repos = state.data as List<RepoItem>,
                onNavigateToRepoItem = onNavigateToRepoItem,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListContent(
    onLogout: () -> Unit,
    repos: List<RepoItem>,
    onNavigateToRepoItem: (ownerName: String, repoName: String) -> Unit
) {
    Scaffold(
        topBar = {

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "email.com",
                    )
                },
                actions = {
                    IconButton(
                        onClick = onLogout,
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "ícone sair do app",
                            Modifier
                                .clip(CircleShape)
                                .padding(8.dp),
                        )
                    }
                },
                modifier = Modifier.padding(0.dp)
            )

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
                            onNavigateToRepoItem(
                                item.owner.name, item.name
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun previewListScreen() {
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
            onNavigateToRepoItem = { _: String, _: String -> },
            onLogout = { }
        )
    }
}