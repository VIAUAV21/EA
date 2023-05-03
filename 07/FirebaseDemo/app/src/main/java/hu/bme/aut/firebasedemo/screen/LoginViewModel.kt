package hu.bme.aut.firebasedemo.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

sealed interface LoginUiState {
    object Init : LoginUiState
    object LoginSuccess : LoginUiState
    object RegisterSuccess : LoginUiState
    data class Error(val error: String?) : LoginUiState
    object Loading : LoginUiState
}

class LoginViewModel() : ViewModel() {

    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Init)

    private lateinit var auth: FirebaseAuth

    init {
        auth = Firebase.auth
    }


    suspend fun loginUser(email: String, password: String): AuthResult? {
        loginUiState = LoginUiState.Loading
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()

            loginUiState = LoginUiState.LoginSuccess

            result
        } catch (e: Exception) {
            loginUiState = LoginUiState.Error(e.message)

            null
        }
    }

    public fun registerUser(email: String, password: String) {
        loginUiState = LoginUiState.Loading
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                loginUiState = LoginUiState.RegisterSuccess
            }.addOnFailureListener {
                loginUiState = LoginUiState.Error(it.message)
            }
        } catch (e: Exception) {
            loginUiState = LoginUiState.Error(e.message)
        }
        /*viewModelScope.launch {
            loginUiState = try {
                // firebase
                LoginUiState.RegisterSuccess
            } catch (e: Exception) {
                LoginUiState.Error
            }
        }*/
    }
}