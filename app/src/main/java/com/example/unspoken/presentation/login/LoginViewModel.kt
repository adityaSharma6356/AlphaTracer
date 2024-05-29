package com.example.unspoken.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unspoken.domain.apis.EmailValidationApi
import com.example.unspoken.domain.apis.EmailValidationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailDomainVerificationApi: EmailValidationApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun checkEmailDomain() {
        viewModelScope.launch {
            _uiState.update { it.copy(checkingEmail = true) }
            Log.d("emailCheck", "checking")
            try {
                val response = emailDomainVerificationApi.validateEmail(request = EmailValidationRequest(uiState.value.email))
                response.body()?.data?.let {  emailValid ->
                    _uiState.update { it.copy(emailVerified = emailValid) }
                    _uiState.update { it.copy(emailIsOfCollegeDomain = emailValid) }
                    _uiState.update { it.copy(continueSignUp = true) }
                }
                Log.d("emailCheck", response.body()?.data.toString())
                Log.d("emailCheck", response.code().toString())
                Log.d("emailCheck", response.message())
            } catch (e: Exception) {
                Log.d("emailCheck", e.message.toString())
            }
            _uiState.update { it.copy(checkingEmail = false) }
        }
    }

    fun signUp(){

    }

    fun onEmailChange(email: String){
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String){
        _uiState.update { it.copy(password = password) }
    }

    fun selectLogin() {
        _uiState.update { it.copy(selectedLogIn = true) }
    }

    fun selectSignUp() {
        _uiState.update { it.copy(selectedLogIn = false, emailVerified = false, emailIsOfCollegeDomain = true, continueSignUp = false) }
    }
}

data class LoginUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val checkingEmail: Boolean = false,
    val selectedLogIn: Boolean = true,
    val continueSignUp: Boolean = false,
    val emailVerified: Boolean = false,
    val emailIsOfCollegeDomain: Boolean = true
)
