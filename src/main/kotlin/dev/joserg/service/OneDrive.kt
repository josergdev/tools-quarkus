package dev.joserg.service

import dev.joserg.client.MicrosoftPersonalClient
import dev.joserg.client.MicrosoftPersonalClient.FileInfo
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.net.URI

@Singleton
class OneDrive {

    @Inject
    lateinit var tokenClaimer: TokenClaimer

    @Inject
    lateinit var redeemInterceptor: RedeemInterceptor

    @RestClient
    lateinit var microsoftPersonalClient: MicrosoftPersonalClient

    fun fileInfo(uri: URI): Uni<FileInfo> {
        return claimTokenAndRedeem(uri).flatMap { tokenAndRedeem ->
            microsoftPersonalClient.fileInfo(tokenAndRedeem.redeem, tokenAndRedeem.token)
        }
    }

    private fun claimTokenAndRedeem(url: URI) =
        Uni.combine().all().unis(
            tokenClaimer.claim(),
            redeemInterceptor.intercept(url)
        ).asTuple().map { tuple ->
            TokenAndRedeem(tuple.item1.token, tuple.item2.value)
        }

    data class TokenAndRedeem(val token: String, val redeem: String)
}