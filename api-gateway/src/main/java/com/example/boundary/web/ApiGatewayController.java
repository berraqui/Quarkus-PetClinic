package com.example.boundary.web;

import com.example.application.CustomersServiceClient;
import com.example.application.VisitsServiceClient;
import com.example.dto.OwnerDetails;
import com.example.dto.Visits;
import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.function.Function;
import java.util.stream.Collectors;

@Path("/api/gateway")
public class ApiGatewayController {

    @Inject
    @RestClient
    CustomersServiceClient customersServiceClient;

    @Inject
    @RestClient
    VisitsServiceClient visitsServiceClient;

    @GET
    @Path("/owners/{ownerId}")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(requestVolumeThreshold = 4)
    @Timeout(5000)
    public Uni<OwnerDetails> getOwnerDetails(@PathParam("ownerId") int ownerId) {
        return customersServiceClient.getOwner(ownerId)
                .flatMap(owner -> visitsServiceClient.getVisitsForPets(owner.getPetIds())
                        .onFailure().recoverWithUni(Uni.createFrom().item(new Visits()))
                        .map(addVisitsToOwner(owner)));
    }

    private Function<Visits, OwnerDetails> addVisitsToOwner(OwnerDetails owner) {
        return visits -> {
            owner.getPets()
                    .forEach(pet -> pet.getVisits()
                            .addAll(visits.getItems().stream()
                                    .filter(v -> v.getPetId() == pet.getId())
                                    .collect(Collectors.toList()))
                    );
            return owner;
        };
    }
}