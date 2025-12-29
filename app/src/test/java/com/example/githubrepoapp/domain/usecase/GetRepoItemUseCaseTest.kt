package com.example.githubrepoapp.domain.usecase

import com.example.githubrepoapp.domain.remote.model.Owner
import com.example.githubrepoapp.domain.remote.model.RepoItem
import com.example.githubrepoapp.domain.remote.repository.GithubRemoteRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetRepoItemUseCaseTest {

    lateinit var repository: GithubRemoteRepository
    lateinit var useCase: GetRepoItemUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mockk<GithubRemoteRepository>()
        useCase = GetRepoItemUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `GetRepoItemUseCase should return Result success when repository returns an RepoItem`() =
        runTest {
            val repoItem = RepoItem("desc", "repo1", "owner1/repo1", Owner("", "owner1"))
            coEvery { repository.getRepoItem(any(), any()) } returns repoItem

            val result = useCase("owner1", "repo1")

            assertEquals(result, Result.success(repoItem))
            coVerify(exactly = 1) { repository.getRepoItem("owner1", "repo1") }
        }

    @Test
    fun `GetRepoItemUseCase should return Result failure when repository throws an Exception`() =
        runTest {
            val exception = Exception()
            coEvery { repository.getRepoItem(any(), any()) } throws exception

            val result = useCase("owner", "repo")

            assertEquals(result.exceptionOrNull(), exception)
            coVerify(exactly = 1) { repository.getRepoItem(any(), any()) }
        }
}