package dev.joserg.client

import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Path
import jakarta.ws.rs.POST
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@RegisterRestClient
interface TokenClient {

    @POST
    @Path("/v1.0/token")
    fun post(body: TokenRequest): Uni<TokenResponse>

    data class TokenRequest(val appId: String)
    data class TokenResponse(val token: String)
}