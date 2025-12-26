package com.example.githubrepoapp.presentation.repoitem

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoapp.domain.remote.model.RepoItem
import com.example.githubrepoapp.domain.usecase.GetRepoItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoItemViewModel @Inject constructor(
    private val getRepoItemUseCase: GetRepoItemUseCase
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun loadItem(ownerName: String, repoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getRepoItemUseCase(
                ownerName = ownerName,
                repoName = repoName
            )
                .fold(
                    onSuccess =
                        { item ->
                            _stateFlow.update { _ ->
                                State.RepoItemData(item)
                            }
                        },
                    onFailure = { Log.d("Exception", it.message.toString()) }
                )
        }
    }
}

sealed class State {
    object Error : State()
    object Loading : State()
    data class RepoItemData(
        val repoItem: RepoItem
    ) : State()
}