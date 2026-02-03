package com.example.githubrepoapp.presentation.repolist

import android.util.Log
import com.example.githubrepoapp.analytics.AnalyticsService
import com.example.githubrepoapp.domain.remote.auth.usecase.AuthState
import com.example.githubrepoapp.domain.remote.auth.usecase.MonitorAuthenticationUseCase
import com.example.githubrepoapp.domain.remote.repositories.model.repo1
import com.example.githubrepoapp.domain.remote.repositories.model.repo2
import com.example.githubrepoapp.domain.remote.repositories.model.repo3
import com.example.githubrepoapp.domain.remote.repositories.usecase.GetRepoListUseCase
import com.example.githubrepoapp.presentation.baseviewmodel.State
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepoListViewModelTest {
    private val getRepoListUseCase = mockk<GetRepoListUseCase>()
    private val monitorAuthenticationUseCase = mockk<MonitorAuthenticationUseCase>()
    private val analyticsService = mockk<AnalyticsService>()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: RepoListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RepoListViewModel(getRepoListUseCase, monitorAuthenticationUseCase, analyticsService, testDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `Given RepoListViewModel initialize with unauthenticated AuthState should call restart app`() =
        runTest {
            val mockRestartApp = mockk<() -> Unit>()
            val unauthenticated = flow { emit(AuthState.Unauthenticated) }
            justRun { mockRestartApp() }
            every { monitorAuthenticationUseCase() } returns unauthenticated

            viewModel.initialize(mockRestartApp)
            advanceUntilIdle()

            verify(exactly = 1) { mockRestartApp() }
        }

    @Test
    fun `Given RepoListViewModel getRepoList when succeed to get repo list then should update stateFlow to State Success`() =
        runTest {
            val spy = spyk(viewModel)
            val list = listOf(repo1, repo2, repo3)
            coEvery { getRepoListUseCase() } returns Result.success(list)

            spy.getRepoList()
            advanceUntilIdle()

            assertEquals(State.Success(list), spy.stateFlow.value)
        }

    @Test
    fun `Given RepoListViewModel getRepoList when fail to get repo list then should log exception`() =
        runTest {
            val spy = spyk(viewModel)
            mockkStatic(Log::class)
            every { Log.d(any(), any()) } returns 0
            coEvery { getRepoListUseCase() } returns Result.failure(kotlin.Exception())

            spy.getRepoList()

            assertEquals(State.Loading, spy.stateFlow.value)
            verify { Log.d(any(), any()) }
        }

    @Test
    fun `Given RepoListViewModel when onItemClick is called should send analytics event log`() =
        runTest {
            val mockRepoItem = repo1
            coJustRun { analyticsService.logRepoItemClick(any()) }

            viewModel.onItemClick(mockRepoItem)

            coVerify(exactly = 1) { analyticsService.logRepoItemClick(any()) }
        }

}