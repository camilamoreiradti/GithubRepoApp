package com.example.githubrepoapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.githubrepoapp.presentation.navigation.MainNavHost
import com.example.githubrepoapp.presentation.splash.SplashViewModel
import com.example.githubrepoapp.ui.theme.GithubRepoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            viewModel.isLoading.value
        }
        setContent {
            val context = LocalContext.current
            GithubRepoAppTheme {
                Box(
                    modifier = Modifier
                        .safeDrawingPadding()
                ) {
                    MainNavHost(
                        toAccount = {
                            context.startActivity(
                                Intent(
                                    this@MainActivity,
                                    AccountActivity::class.java
                                )
                            )
                        },
                        toLogIn = {
                            context.startActivity(
                                Intent(
                                    this@MainActivity,
                                    AuthActivity::class.java
                                )
                            )
                            finish()
                        },
                        splashViewModel = viewModel,
                    )
                }
            }
        }
    }
}