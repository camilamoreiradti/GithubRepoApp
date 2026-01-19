package com.example.githubrepoapp.presentation.repolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoapp.analytics.AnalyticsService
import com.example.githubrepoapp.analytics.firebase.FirebaseAnalyticsService
import com.example.githubrepoapp.domain.remote.auth.usecase.AuthState
import com.example.githubrepoapp.domain.remote.auth.usecase.MonitorAuthenticationUseCase
import com.example.githubrepoapp.domain.remote.repositories.model.RepoItem
import com.example.githubrepoapp.domain.remote.repositories.usecase.GetRepoListUseCase
import com.example.githubrepoapp.presentation.baseviewmodel.State
import com.example.githubrepoapp.presentation.baseviewmodel.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val getRepoListUseCase: GetRepoListUseCase,
    private val monitorAuthenticationUseCase: MonitorAuthenticationUseCase,
    private val analyticsService: AnalyticsService
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<State<List<RepoItem>>>(State.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun initialize(restartApp: () -> Unit) {
        viewModelScope.launch {
            monitorAuthenticationUseCase().collect { authState ->
                if (authState is AuthState.Unauthenticated) restartApp()
            }
        }
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun getRepoList() {
        viewModelScope.launch(Dispatchers.IO) {
            getRepoListUseCase().fold(
                onSuccess = { list ->
                    _stateFlow.update { _ ->
                        State.Success(list)
                    }
                },
                onFailure = { Log.d("Exception", it.message.toString()) }
            )
        }
    }

    fun onItemClick(repoItem: RepoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val log = mapOf("item_name" to repoItem.name, "item_owner" to repoItem.owner.name)
            analyticsService.logRepoItemClick(log)
        }
    }
}