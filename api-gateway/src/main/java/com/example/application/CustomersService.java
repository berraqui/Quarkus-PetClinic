package com.example.application;

import com.example.dto.OwnerDetails;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@RegisterRestClient
@Path("/owners")
public interface CustomersService {

    @GET
    @Path("/{ownerId}")
    Uni<OwnerDetails> getOwner(@PathParam("ownerId") int ownerId);
}


