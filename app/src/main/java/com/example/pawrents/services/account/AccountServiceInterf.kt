package com.example.pawrents.services.account


import com.example.pawrents.classes.User
import kotlinx.coroutines.flow.Flow

    interface AccountService {
        val currentUserId: String
        val hasUser: Boolean

        val currentUser: Flow<User>

        suspend fun authenticate(email: String, password: String)
        suspend fun sendRecoveryEmail(email: String)
        suspend fun createAnonymousAccount()
        suspend fun linkAccount(email: String, password: String)
        suspend fun deleteAccount()
        suspend fun signOut()
    }