package com.example.karyaconnectnepal

import com.example.karyaconnectnepal.Repository.AuthRepoImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class AuthUnitTest {
    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    private lateinit var authRepo: AuthRepoImpl

    @Captor
    private lateinit var captor: ArgumentCaptor<OnCompleteListener<AuthResult>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        authRepo = AuthRepoImpl(mockAuth)
    }

    @Test
    fun testRegister_Successful() {
        val email = "test@example.com"
        val password = "testPassword"
        var expectedResult = ""

        `when`(mockAuth.createUserWithEmailAndPassword(any(), any())).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(true)

        val callback = { success: Boolean, message: String -> expectedResult = message }

        authRepo.register(email, password, callback)
        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Registration successful", expectedResult)
    }

    @Test
    fun testRegister_Failure() {
        val email = "test@example.com"
        val password = "testPassword"
        var expectedResult = ""

        `when`(mockAuth.createUserWithEmailAndPassword(any(), any())).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(Exception("Registration failed"))

        val callback = { success: Boolean, message: String -> expectedResult = message }

        authRepo.register(email, password, callback)
        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Registration failed", expectedResult)
    }

    @Test
    fun testLogin_Successful() {
        val email = "test@example.com"
        val password = "testPassword"
        var expectedResult = ""

        `when`(mockAuth.signInWithEmailAndPassword(any(), any())).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(true)

        val callback = { success: Boolean, message: String -> expectedResult = message }

        authRepo.login(email, password, callback)
        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Login successful", expectedResult)
    }

    @Test
    fun testLogin_Failure() {
        val email = "test@example.com"
        val password = "testPassword"
        var expectedResult = ""

        `when`(mockAuth.signInWithEmailAndPassword(any(), any())).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(Exception("Login failed"))

        val callback = { success: Boolean, message: String -> expectedResult = message }

        authRepo.login(email, password, callback)
        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertEquals("Login failed", expectedResult)
    }
}