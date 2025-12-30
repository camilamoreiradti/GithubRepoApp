package com.example.githubrepoapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.githubrepoapp.domain.remote.repositories.model.RepoItem
import com.example.githubrepoapp.domain.remote.repositories.model.repo1
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme

@Composable
fun RepoListItem(
    repo: RepoItem,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onItemClicked,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(
                    LocalContext.current)
                    .data(repo.owner.profilePhoto)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile Photo",
                modifier = Modifier
                    .width(60.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            repo.fullName.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Details",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun previewRepoListItem() {
    GithubRepoAppTheme() { 
        RepoListItem(
            repo = repo1,
            onItemClicked = {},
        )
    }
}