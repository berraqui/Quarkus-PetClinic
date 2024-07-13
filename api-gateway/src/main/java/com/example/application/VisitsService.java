package com.example.application;

import com.example.dto.Visits;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.List;

@RegisterRestClient
@Path("/pets/visits")
public interface VisitsService {

    @GET
    Uni<Visits> getVisitsForPets(@QueryParam("petId") List<Integer> petIds);
}