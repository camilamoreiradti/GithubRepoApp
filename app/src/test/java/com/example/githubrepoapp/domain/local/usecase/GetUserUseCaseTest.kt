package com.example.githubrepoapp.domain.local.usecase

import com.example.githubrepoapp.domain.local.model.UserLocal
import com.example.githubrepoapp.domain.local.repository.UserLocalRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserUseCaseTest {
    private val userLocalRepository = mockk<UserLocalRepository>()
    private val getUserUseCase = GetUserUseCase(userLocalRepository)

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `Given GetUserUseCase when user is logged should return current user`() = runTest {
        val expectedUser = UserLocal("uid1234", "email@email.com")
        every { userLocalRepository.currentUser } returns flow { emit(expectedUser) }
        val result = mutableListOf<UserLocal>()

        getUserUseCase().collect { result.add(it) }

        assertTrue(result.contains(expectedUser))
    }

    @Test
    fun `Given GetUserUseCase when user is logged should return empty flow`() = runTest {
        every { userLocalRepository.currentUser } returns flow { }
        val result = mutableListOf<UserLocal>()

        getUserUseCase().collect { result.add(it) }

        assertTrue(result.isEmpty())
    }
}