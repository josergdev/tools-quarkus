package dev.joserg.service

import dev.joserg.client.MicrosoftPersonalClient
import dev.joserg.client.MicrosoftPersonalClient.FileInfo
import dev.joserg.client.TokenClient
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.tuples.Tuple2
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

    fun fileInfo(uri: URI): Uni<FileInfo> = combineTokenAndRedeem(uri).chain(::fileInfo)

    private fun combineTokenAndRedeem(url: URI) =
        Uni.combine().all()
            .unis(tokenClaimer.claim(), redeemInterceptor.intercept(url))
            .asTuple().map(::TokenAndRedeem)

    private fun fileInfo(tokenAndRedeem: TokenAndRedeem) =
        microsoftPersonalClient.fileInfo(tokenAndRedeem.redeem, tokenAndRedeem.token)

    data class TokenAndRedeem(val token: String, val redeem: String) {
        constructor(tuple: Tuple2<TokenClient.TokenResponse, RedeemInterceptor.Redeem>) : this(
            tuple.item1.token,
            tuple.item2.value
        )
    }


}

