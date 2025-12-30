package com.example.githubrepoapp.domain.usecase

import com.example.githubrepoapp.domain.remote.items.model.Owner
import com.example.githubrepoapp.domain.remote.items.model.RepoItem
import com.example.githubrepoapp.domain.remote.items.repository.GithubRemoteRepository
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetRepoListUseCaseTest {

    private lateinit var repository: GithubRemoteRepository
    private lateinit var useCase: GetRepoListUseCase


    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = mockk<GithubRemoteRepository>()
        useCase = GetRepoListUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `getRepoListUseCase should return Result success when repository returns a list`() =
        runTest {
            val listOfRepos = listOf<RepoItem>(
                RepoItem("desc", "repo1", "owner1/repo1", Owner("", "owner1")),
                RepoItem("desc", "repo2", "owner2/repo2", Owner("", "owner2")),
            )

            val expectedResult = Result.success(listOfRepos)

            coEvery { repository.getRepoList() } returns listOfRepos

            val result = useCase()

            assertEquals(result, expectedResult)
            // coVerify serve para ver se uma função MOCKADA foi chamada
            coVerify(exactly = 1) { repository.getRepoList() }
        }

    @Test
    fun `getRepoListUseCase should return Result failure when repository throws an exception`() =
        runTest {
            val exception = Exception()
            coEvery { repository.getRepoList() } throws exception

            val result = useCase()

            assertTrue(result.isFailure)
            assertEquals(result.exceptionOrNull(), exception)

            coVerify(exactly = 1) { repository.getRepoList() }
        }
}