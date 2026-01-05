package com.example.githubrepoapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubrepoapp.presentation.navigation.MainNavHost
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            GithubRepoAppTheme {
                Box(
                    modifier = Modifier
                        .safeDrawingPadding()
                ) {
                    MainNavHost(
                        toSignIn = {
                            context.startActivity(
                                Intent(
                                    this@MainActivity,
                                    AuthActivity::class.java
                                )
                            )
                            finish()
                        }
                    )
                }
            }
        }
    }
}