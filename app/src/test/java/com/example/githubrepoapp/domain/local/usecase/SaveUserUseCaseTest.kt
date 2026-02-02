package com.example.githubrepoapp.domain.local.usecase

import com.example.githubrepoapp.domain.local.repository.UserLocalRepository
import io.mockk.clearAllMocks
import io.mockk.coJustRun
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

@OptIn(ExperimentalCoroutinesApi::class)
class SaveUserUseCaseTest {
    private val userLocalRepository = mockk<UserLocalRepository>()
    private val saveUserUseCase = SaveUserUseCase(userLocalRepository)

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
    fun `Given SaveUserCase should call save user function with user data`() = runTest {
        val userId = "uid1234"
        val userEmail = "email@email.com"
        coJustRun { userLocalRepository.saveUser(any(), any()) }

        saveUserUseCase(userId, userEmail)

        coVerify { userLocalRepository.saveUser(userId, userEmail) }
    }
}