package com.stackdev.controllers

import com.stackdev.models.ClientDetails
import com.stackdev.service.ClientDetailsService
import io.smallrye.common.annotation.Blocking
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Blocking
class ClientDetailsResource(private val service: ClientDetailsService) {

    @POST
    @Path("/create")
    suspend fun create(client: ClientDetails): Response {
        val created = service.create(client)
        return Response.status(Response.Status.CREATED).entity(created).build()
    }

    @GET
    @Path("/get/list-all")
    suspend fun listAll(): List<ClientDetails> = service.listAll()

    @GET
    @Path("/{id}")
    suspend fun getById(@PathParam("id") id: Long): ClientDetails? =
        service.findById(id)

    @PUT
    @Path("/update/{id}")
    suspend fun update(
        @PathParam("id") id: Long,
        client: ClientDetails,
    ): Response {
        val updated = service.update(id, client)
        return Response.ok(updated).build()
    }

    @DELETE
    @Path("/delete/{id}")
    suspend fun delete(@PathParam("id") id: Long): Response {
        service.deleteById(id)
        return Response.noContent().build()
    }
}