package com.example.githubrepoapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.githubrepoapp.presentation.navigation.AccountNavHost
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // acessar o context (minha activity) em
            val context = LocalContext.current
            GithubRepoAppTheme {
                Box(
                    modifier = Modifier
                        .safeDrawingPadding()
                ) {
                    AccountNavHost(
                        onLogOut = {
                            context.startActivity(
                                Intent(
                                    this@AccountActivity,
                                    AuthActivity::class.java
                                )
                            )
                            finish()
                        },
                        toRepoList = {
                            finish()
                        }
                    )
                }
            }
        }
    }
}