package dev.joserg.client

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.rest.client.reactive.ClientQueryParam
import io.quarkus.rest.client.reactive.NotBody
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import java.net.URI

@RegisterRestClient
interface MicrosoftPersonalClient : ClientHeadersFactory {


    @Path("/_api/v2.0/shares/u!{redeem}/driveItem")
    @GET
    @ClientQueryParam(name = "action", value = ["EmbedView"])
    @ClientQueryParam(
        name = "\$select",
        value = ["content.downloadUrl", "file", "sharepointIds", "sensitivityLabel", "webUrl", "webDavUrl", "parentReference", "vault"]
    )
    @ClientHeaderParam(name = "authorization", value = ["Badger {token}"])
    @ClientHeaderParam(name = "accept", value = ["application/json"])
    @ClientHeaderParam(name = "accept-language", value = ["es-ES,es;q=0.9"])
    @ClientHeaderParam(name = "origin", value = ["https://onedrive.live.com"])
    @ClientHeaderParam(name = "prefer", value = ["autoredeem"])
    @ClientHeaderParam(name = "priority", value = ["u=1, i"])
    @ClientHeaderParam(name = "referer", value = ["https://onedrive.live.com/"])
    @ClientHeaderParam(name = "sec-ch-ua", value = ["\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\""])
    @ClientHeaderParam(name = "sec-ch-ua-mobile", value = ["?0"])
    @ClientHeaderParam(name = "sec-ch-ua-platform", value = ["\"Windows\""])
    @ClientHeaderParam(name = "sec-fetch-dest", value = ["empty"])
    @ClientHeaderParam(name = "sec-fetch-mode", value = ["cors"])
    @ClientHeaderParam(name = "sec-fetch-site", value = ["cross-site"])
    @ClientHeaderParam(name = "user-agent", value = ["Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36"])
    fun fileInfo(@PathParam("redeem") redeem: String, @NotBody token: String): Uni<FileInfo>

    data class FileInfo(@JsonProperty("@content.downloadUrl") val downloadUrl: URI)
}