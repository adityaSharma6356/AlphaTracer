package com.example.unspoken.domain.apis

import com.google.errorprone.annotations.Keep
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface EmailValidationApi {
    @POST("validate/email/academic")
    suspend fun validateEmail(
        @Header("apy-token") token: String = "APY0tubbwVQFbua2Fa8ojHPwJjpbgtzAcc9zXLAIlYjHzQUJ1U014fFHBRFvKNbuo",
        @Header("Content-Type") type: String = "application/json",
        @Body request: EmailValidationRequest
    ): Response<EmailValidationResponse>
}

data class EmailValidationResponse(
    @SerializedName("data")
    val data: Boolean
)

@Keep
data class EmailValidationRequest(
    @SerializedName("email")
    val email: String
)
