package com.example.githubrepoapp.presentation.repolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoapp.domain.remote.items.model.RepoItem
import com.example.githubrepoapp.presentation.baseviewmodel.State
import com.example.githubrepoapp.domain.usecase.GetRepoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val getRepoListUseCase: GetRepoListUseCase,
) : ViewModel() {

    private val _stateFlow= MutableStateFlow<State<List<RepoItem>>>(State.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun getRepoList() {
        viewModelScope.launch(Dispatchers.IO) {
            getRepoListUseCase().fold(
                onSuccess = {  list ->
                    _stateFlow.update { _->
                        State.Success(list)
                    }
                },
                onFailure = { Log.d("Exception", it.message.toString()) }
            )
        }
    }
}