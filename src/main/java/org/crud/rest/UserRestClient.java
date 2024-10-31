package org.crud.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.crud.dto.UserDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/usuarios")
@RegisterRestClient(baseUri = "http://localhost:8080")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public interface UserRestClient {

    @GET
    @Path("/{id}")
    UserDto getUserById(@PathParam("id") Long id);
}
