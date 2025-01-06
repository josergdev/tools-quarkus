package dev.joserg.service

import dev.joserg.client.TokenClient
import dev.joserg.client.TokenClient.TokenRequest
import jakarta.inject.Singleton
import org.eclipse.microprofile.rest.client.inject.RestClient

@Singleton
class TokenClaimer {

    @RestClient
    lateinit var tokenClient: TokenClient

    fun claim() = tokenClient.post(TokenRequest("00000000-0000-0000-0000-0000481710a4"))

}