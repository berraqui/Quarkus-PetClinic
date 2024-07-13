package com.example.model;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PetRepository implements PanacheRepository<Pet> {
    public List<PetType> findPetTypes() {
        return getEntityManager().createQuery("SELECT ptype FROM PetType ptype ORDER BY ptype.name", PetType.class)
                .getResultList();
    }

    /**
     * Find a PetType by its ID.
     * @param typeId the ID of the PetType.
     * @return an Optional containing the PetType if found, or empty if not found.
     */
    public Optional<PetType> findPetTypeById(int typeId) {
        return getEntityManager().createQuery("FROM PetType ptype WHERE ptype.id = :typeId", PetType.class)
                .setParameter("typeId", typeId)
                .getResultStream()
                .findFirst();
    }
}

