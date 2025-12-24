package com.example.githubrepoapp.presentation.repolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoapp.data.remote.mapper.fromDTOList
import com.example.githubrepoapp.domain.remote.model.RepoItem
import com.example.githubrepoapp.domain.usecase.GetRepoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.fold

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val getRepoListUseCase: GetRepoListUseCase,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun getRepoList() {
        viewModelScope.launch(Dispatchers.IO) {
            getRepoListUseCase().fold(
                onSuccess = {  list ->
                    _stateFlow.update { _->
                        State.RepoList(list)
                    }
                },
                onFailure = { Log.d("Exception", it.message.toString()) }
            )
        }
    }
}

sealed class State {
    object Error: State()
    object Loading : State()
    data class RepoList(
        val repoList: List<RepoItem>
    ) : State()
}