package com.example.web;

import com.example.model.*;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/pet")
@ApplicationScoped
public class PetResource {

    @Inject
    PetRepository petRepository;

    @Inject
    OwnerRepository ownerRepository;

    @Inject
    Logger log;

    @GET
    @Path("/petTypes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PetType> getPetTypes() {
        return petRepository.findPetTypes();
    }

    @POST
    @Path("/owners/{ownerId}/pets")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processCreationForm(@PathParam("ownerId") @Min(1) long ownerId, PetRequest petRequest) {

        Owner owner = ownerRepository.findById(ownerId);

        Pet pet = new Pet();
        owner.addPet(pet);
        Pet savedPet = save(pet, petRequest);

        return Response.status(Response.Status.CREATED).entity(savedPet).build();
    }

    @PUT
    @Path("/owners/*/pets/{petId}")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response processUpdateForm(PetRequest petRequest) {
        long petId = petRequest.id();
        Pet pet = findPetById(petId);
        //save(petRequest);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private Pet save(Pet pet, PetRequest petRequest) {
        pet.setName(petRequest.name());
        pet.setBirthDate(petRequest.birthDate());

        petRepository.findPetTypeById(petRequest.typeId())
                .ifPresent(pet::setType);

        log.infof("Saving Pet {}", pet);
        petRepository.persist(pet);
        return pet;
    }

    @GET
    @Path("/owners/*/pets/{petId}")
    @Produces(MediaType.APPLICATION_JSON)
    public PetDetails findPet(@PathParam("petId") Long petId) {
        Pet pet = findPetById(petId);
        return new PetDetails(pet);
    }

    private Pet findPetById(Long petId) {
        return petRepository.findById(petId);
    }
}
