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
        authRepo = AuthRepoImpl(mockAuth) // Corrected to use AuthRepoImpl
    }

    @Test
    fun testRegister_Successful() {
        val email = "test@example.com"
        val password = "testPassword"
        var expectedResult = "Initial Value" // Initial value to check update

        // Mocking FirebaseAuth behavior
        `when`(mockAuth.createUserWithEmailAndPassword(any(), any())).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(true)

        // Callback that updates expectedResult
        val callback = { success: Boolean, message: String ->
            expectedResult = message
        }

        // Call the function under test
        authRepo.register(email, password, callback)

        // Capture the listener and trigger onComplete
        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        // Assert expected message
        assertEquals("Registration successful", expectedResult) // Ensure message matches AuthRepoImpl
    }
}
