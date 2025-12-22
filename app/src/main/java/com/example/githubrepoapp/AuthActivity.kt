package com.example.githubrepoapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubrepoapp.presentation.navigation.AuthNavHost
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            GithubRepoAppTheme {
                Box(
                    modifier = Modifier
                        .safeDrawingPadding()
                ) {
                    AuthNavHost(
                        onAuthSuccess = {
                            // Intent: intenção de navegar da Activity atual (this) para a MainActivity
                            // startActivity: executa a intenção e inicia a nova activity
                            startActivity(Intent(this@AuthActivity, MainActivity::class.java))

                            // Destrói AuthActivity atual, cria fluxo de navegação SEM VOLTA
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    GithubRepoAppTheme {
    }
}