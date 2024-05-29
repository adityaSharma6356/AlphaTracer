package com.example.unspoken.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainViewModel @Inject constructor(
    auth: FirebaseAuth
) : ViewModel() {
    val isLoggedIn = auth.authStateFlow().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        auth.currentUser != null
    )
}

inline val FirebaseAuth?.currentUserId: String?
    get() = this?.currentUser?.uid

fun FirebaseAuth.authStateFlow(): Flow<Boolean> = callbackFlow {
    val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        trySend(firebaseAuth.currentUser != null)
    }

    addAuthStateListener(authStateListener)

    awaitClose {
        removeAuthStateListener(authStateListener)
    }
}
