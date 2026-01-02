package com.example.githubrepoapp.presentation.splash

import androidx.lifecycle.ViewModel
import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import com.example.githubrepoapp.presentation.baseviewmodel.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<State<Boolean>>(State.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun onAppStart() {
        if (accountService.hasUser()) {
            _stateFlow.update { State.Success(true) }
        } else {
            _stateFlow.update { State.Error }
        }
    }
}