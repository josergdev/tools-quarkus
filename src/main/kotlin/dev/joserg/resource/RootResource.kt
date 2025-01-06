package dev.joserg.resource

import dev.joserg.service.RedeemInterceptor.Redeem
import dev.joserg.client.MicrosoftPersonalClient.FileInfo
import dev.joserg.client.TokenClient.TokenResponse
import dev.joserg.service.OneDrive
import dev.joserg.service.RedeemInterceptor
import dev.joserg.service.TokenClaimer
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.net.URI

@Path("/")
class RootResource {

    @Inject
    lateinit var tokenClaimer: TokenClaimer

    @Inject
    lateinit var redeemInterceptor: RedeemInterceptor

    @Inject
    lateinit var oneDrive: OneDrive

    @GET
    @Path("token")
    @Produces(MediaType.APPLICATION_JSON)
    fun token(): Uni<TokenResponse> = tokenClaimer.claim()

    @GET
    @Path("redeem")
    @Produces(MediaType.APPLICATION_JSON)
    fun redeem(@QueryParam("url") url: String): Uni<Redeem> = redeemInterceptor.intercept(URI.create(url))


    @GET
    @Path("link")
    @Produces(MediaType.APPLICATION_JSON)
    fun link(@QueryParam("url") url: String, @QueryParam("redirect") redirect: Boolean?): Uni<Response> =
        oneDrive.fileInfo(URI.create(url)).map { fileInfo ->
        if (redirect == true)
            Response.status(Response.Status.MOVED_PERMANENTLY)
                .location(fileInfo.downloadUrl)
                .build()
        else
            Response.ok(fileInfo).build()
    }
}
