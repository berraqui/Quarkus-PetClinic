package com.example.application;

import com.example.application.CustomersService;
import com.example.dto.OwnerDetails;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CustomersServiceClient {

    @Inject
    @RestClient
    CustomersService customersService;

    public Uni<OwnerDetails> getOwner(final int ownerId) {
        return customersService.getOwner(ownerId);
    }
}