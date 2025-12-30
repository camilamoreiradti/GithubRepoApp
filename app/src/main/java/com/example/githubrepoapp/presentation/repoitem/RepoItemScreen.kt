package com.example.githubrepoapp.presentation.repoitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.githubrepoapp.domain.remote.items.model.RepoItem
import com.example.githubrepoapp.domain.remote.items.model.repo1
import com.example.githubrepoapp.presentation.components.InfoSection
import com.example.githubrepoapp.presentation.components.LoadingIndicator
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme
import com.example.githubrepoapp.presentation.baseviewmodel.State

@Composable
fun RepoItemScreen(
    ownerName: String,
    repoName: String,
    navigateBack: () -> Unit
) {

    val viewModel: RepoItemViewModel = hiltViewModel()

    val uiState by viewModel.stateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadItem(
            ownerName = ownerName,
            repoName = repoName
        )
    }

    when (val state = uiState) {
        State.Error -> {}

        State.Loading -> {
            LoadingIndicator()
        }

        is State.Success<*> -> {
            RepoItemContent(
                repo = state.data as RepoItem,
                navigateBack = navigateBack
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoItemContent(
    repo: RepoItem,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
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
                        Text("Repositories")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(repo.owner.profilePhoto)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Photo",
                modifier = Modifier
                    .width(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(
                text = repo.owner.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoSection(
                label = "Repository",
                content = repo.name
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoSection(
                label = "Description",
                content = repo.description,
            )
        }
    }
}

@Preview
@Composable
fun previewRepoItemContent() {
    GithubRepoAppTheme {
        RepoItemContent(
            repo = repo1,
            navigateBack = {}
        )
    }
}