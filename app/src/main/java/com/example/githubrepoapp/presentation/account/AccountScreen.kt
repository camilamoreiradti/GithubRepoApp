package com.example.githubrepoapp.presentation.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubrepoapp.domain.local.model.UserLocal
import com.example.githubrepoapp.presentation.components.InfoSection
import com.example.githubrepoapp.presentation.components.GithubAppTopBar
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme

@Composable
fun AccountScreen() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountContent(
    user: UserLocal,
    onLogout: () -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            GithubAppTopBar(
                navigateBack = navigateBack,
                navigateBackText = "Repositories"
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(30.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Your Profile",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(12.dp))

            InfoSection(
                label = "ID:",
                content = user.id
            )

            InfoSection(
                label = "Email:",
                content = user.email
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = Color.White
                ),
            ) {
                Text("Logout")
            }
        }
    }
}

@Preview
@Composable
fun previewAccountContent() {
    GithubRepoAppTheme {
        AccountContent(
            user = UserLocal("euir48ei", "email@email.com"),
            onLogout = { },
            navigateBack = { }
        )
    }
}