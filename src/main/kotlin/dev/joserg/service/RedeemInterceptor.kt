package dev.joserg.service

import dev.joserg.client.RedeemClient
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.net.URI

@Singleton
class RedeemInterceptor {

    @Inject
    @RestClient
    lateinit var redeemClient: RedeemClient

    fun intercept(uri: URI): Uni<Redeem> =
        redeemClient.get(uri)
            .onItem().transform { response ->
                val location = response.location
                    ?: throw RedeemInterceptionException("Location header not found in the response")


                val params = location.query.split("&").associate {
                    val (key, value) = it.split("=")
                    key to value
                }

                val redeemParam = params["redeem"]
                    ?: throw RedeemInterceptionException("Redeem query param not found (location: $location)")

                Redeem(redeemParam)
            }

    class RedeemInterceptionException(message: String) : RuntimeException(message)
    data class Redeem(val value: String)
}