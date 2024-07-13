package com.example.web;

import com.example.model.Vet;
import com.example.model.VetRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;



import java.util.List;

@Path("/vets")
public class VetResource {

    @Inject
    VetRepository vetRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vet> showResourcesVetList() {
        return vetRepository.findAll().list();
    }
}