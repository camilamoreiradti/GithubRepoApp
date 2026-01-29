package com.example.githubrepoapp.presentation.login

import com.example.githubrepoapp.analytics.AnalyticsService
import com.example.githubrepoapp.domain.local.usecase.SaveUserUseCase
import com.example.githubrepoapp.domain.remote.auth.usecase.LoginUseCase
import com.example.githubrepoapp.presentation.AuthFormEvent
import com.example.githubrepoapp.presentation.baseviewmodel.State
import com.example.githubrepoapp.presentation.baseviewmodel.UiEvent
import com.google.firebase.auth.FirebaseUser
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.collections.mutableListOf

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val loginUseCase = mockk<LoginUseCase>(relaxed = true)
    private val saveUserUseCase = mockk<SaveUserUseCase>(relaxed = true)
    private val analyticsService = mockk<AnalyticsService>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    val viewModel = LoginViewModel(loginUseCase, saveUserUseCase, analyticsService, testDispatcher)
    lateinit var spy: LoginViewModel

    val expectedUser = User("email@email.com", "123456789")
    val firebaseUser = mockk<FirebaseUser>()
    lateinit var events: MutableList<UiEvent>

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        spy = spyk(viewModel)
        events = mutableListOf<UiEvent>()
        val mockState = State.Success(expectedUser)
        every { spy.stateFlow.value } returns mockState
        every { firebaseUser.uid } returns "uid1234"
        every { firebaseUser.email } returns expectedUser.email
        coEvery { loginUseCase(any(), any()) } returns Result.success(firebaseUser)
        coJustRun { saveUserUseCase(any(), any()) }
        coJustRun { analyticsService.logButtonClick(any()) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `Given LoginViewModel when loadScreen is called then should update stateFlow from State Loading to State Success`() =
        runTest {
            assertTrue(viewModel.stateFlow.value is State.Loading)

            viewModel.loadScreen()

            assertEquals(viewModel.stateFlow.value, State.Success(User()))
        }

    @Test
    fun `Given LoginViewModel onEvent when email is changed should update stateflow`() = runTest {
        viewModel.loadScreen()

        viewModel.onEvent(AuthFormEvent.EmailChanged("email"))

        val state = viewModel.stateFlow.value as State.Success
        assertEquals("email", state.data.email)
    }

    @Test
    fun `Given LoginViewModel onEvent when password is changed should update stateflow`() =
        runTest {
            viewModel.loadScreen()

            viewModel.onEvent(AuthFormEvent.PasswordChanged("123456"))

            val state = viewModel.stateFlow.value as State.Success
            assertEquals("123456", state.data.password)
        }

    @Test
    fun `Given LoginViewmodel onLoginClick with correct email and password should save user, send uiEvent and analytics event`() =
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) {
                spy.uiEvent.collect { events.add(it) }
            }

            spy.onLoginClick()
            advanceUntilIdle()

            assertTrue(events.any { it is UiEvent.Navigate<*> })
            assertFalse(events.any { it is UiEvent.ShowSnackbar })

            coVerify(exactly = 1) { loginUseCase(any(), any()) }
            coVerify(exactly = 1) { saveUserUseCase(any(), any()) }
            coVerify(exactly = 1) { analyticsService.logButtonClick(any()) }
        }

    // TESTING SEPARATELY
    @Test
    fun `Given LoginViewmodel onLoginClick with valid email and password should call loginUseCase`() =
        runTest {
            spy.onLoginClick()
            advanceUntilIdle()

            coVerify(exactly = 1) { loginUseCase(any(), any()) }
        }

    @Test
    fun `Given LoginViewmodel onLoginClick with correct email and password should save user`() =
        runTest {
            spy.onLoginClick()
            advanceUntilIdle()

            coVerify(exactly = 1) { saveUserUseCase(any(), any()) }
        }

    @Test
    fun `Given LoginViewmodel onLoginClick with correct email and password should navigate to ListRoute`() =
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) {
                spy.uiEvent.collect { events.add(it) }
            }

            spy.onLoginClick()
            advanceUntilIdle()

            assertTrue(events.any { it is UiEvent.Navigate<*> })
            assertFalse(events.any { it is UiEvent.ShowSnackbar })
            coVerify(exactly = 1) { analyticsService.logButtonClick(any()) }
        }

    @Test
    fun `Given LoginViewmodel onLoginClick with empty email or password should show snackbar message`() =
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) {
                spy.uiEvent.collect { events.add(it) }
            }
            every { spy.stateFlow.value } returns State.Success(User())

            spy.onLoginClick()
            advanceUntilIdle()

            assertFalse(events.any { it is UiEvent.Navigate<*> })
            assertTrue(events.any { it is UiEvent.ShowSnackbar })
            coVerify(exactly = 1) { analyticsService.logButtonClick(any()) }
        }

    @Test
    fun `Given LoginViewmodel onLoginClick with invalid email should show snackbar message`() =
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) {
                spy.uiEvent.collect { events.add(it) }
            }
            every { spy.stateFlow.value } returns State.Success(User("invalid@email", "123456"))

            spy.onLoginClick()
            advanceUntilIdle()

            assertFalse(events.any { it is UiEvent.Navigate<*> })
            assertTrue(events.any { it is UiEvent.ShowSnackbar })
            coVerify(exactly = 1) { analyticsService.logButtonClick(any()) }
        }

    @Test
    fun `Given LoginViewmodel onLoginClick with incorrect email and password should show snackbar message`() =
        runTest {
            backgroundScope.launch(UnconfinedTestDispatcher()) {
                spy.uiEvent.collect { events.add(it) }
            }
            coEvery { loginUseCase(any(), any()) } returns Result.failure(Exception())

            spy.onLoginClick()
            advanceUntilIdle()

            assertFalse(events.any { it is UiEvent.Navigate<*> })
            assertTrue(events.any { it is UiEvent.ShowSnackbar })
            coVerify(exactly = 1) { analyticsService.logButtonClick(any()) }
        }
}