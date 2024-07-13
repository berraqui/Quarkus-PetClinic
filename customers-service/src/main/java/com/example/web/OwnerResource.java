package com.example.web;


import com.example.model.Owner;
import com.example.model.OwnerRepository;
import com.example.web.mapper.OwnerEntityMapper;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/owners")
@ApplicationScoped
public class OwnerResource {

    @Inject
    OwnerRepository ownerRepository;

    @Inject
    OwnerEntityMapper ownerEntityMapper;

    @Inject
    Logger log;

    /**
     * Create Owner
     */
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOwner(@Valid OwnerRequest ownerRequest) {
        Owner owner = ownerEntityMapper.map(new Owner(), ownerRequest);
        ownerRepository.persist(owner);
        return Response.status(Response.Status.CREATED).entity(owner).build();
    }

    /**
     * Read single Owner
     */
    @GET
    @Path("/{ownerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findOwner(@PathParam("ownerId") @Min(1) long ownerId) {
        Optional<Owner> owner = ownerRepository.findByIdOptional(ownerId);
        return owner.map(o -> Response.ok(o).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Read List of Owners
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Owner> findAll() {
        return ownerRepository.listAll();
    }

    /**
     * Update Owner
     */
    @PUT
    @Path("/{ownerId}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOwner(@PathParam("ownerId") @Min(1) long ownerId, @Valid OwnerRequest ownerRequest) {
        Owner ownerModel = ownerRepository.findByIdOptional(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner " + ownerId + " not found"));

        ownerEntityMapper.map(ownerModel, ownerRequest);
        log.infof("Saving owner %s", ownerModel);
        ownerRepository.persist(ownerModel);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
