package com.example.application;

import com.example.dto.Visits;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class VisitsServiceClient {

    @Inject
    @RestClient
    VisitsService visitsService;

    public Uni<Visits> getVisitsForPets(final List<Integer> petIds) {
        return visitsService.getVisitsForPets(petIds);
    }
}
