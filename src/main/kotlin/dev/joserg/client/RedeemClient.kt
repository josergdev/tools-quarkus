package dev.joserg.client

import io.quarkus.rest.client.reactive.NotBody
import io.quarkus.rest.client.reactive.Url
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.GET
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.jboss.resteasy.reactive.RestResponse
import java.net.URI

@RegisterRestClient
interface RedeemClient {

    @GET
    fun get(@Url @NotBody uri: URI): Uni<RestResponse<Any>>

}