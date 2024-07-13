package com.example.web;

import java.util.List;

import com.example.model.Visit;
import com.example.model.VisitRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;




@Path("/visits")
@Slf4j
@RequiredArgsConstructor
public class VisitResource {

    @Inject
    VisitRepository visitRepository;

    @POST
    @Path("/owners/pets/{petId}/visits")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(@Valid Visit visit, @PathParam("petId") @Min(1) int petId) {
        visit.setPetId(petId);
        log.info("Saving visit {}", visit);
        visitRepository.persist(visit);
        return Response.status(Response.Status.CREATED).entity(visit).build();
    }

    @GET
    @Path("/owners/pets/{petId}/visits")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("petId") @Min(1) int petId) {
        List<Visit> visits = visitRepository.findByPetId(petId);
        return Response.ok(visits).build();
    }

    @GET
    @Path("/pets/visits")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@QueryParam("petId") List<Integer> petIds) {
        List<Visit> visits = visitRepository.findByPetIdIn(petIds);
        return Response.ok(new Visits(visits)).build();
    }

    @Value
    static class Visits {
        List<Visit> items;
    }
}
