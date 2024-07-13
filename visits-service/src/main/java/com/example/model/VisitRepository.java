package com.example.model;


import java.util.Collection;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class VisitRepository implements PanacheRepository<Visit> {

    public List<Visit> findByPetId(int petId) {
        return find("petId", petId).list();
    }

    public List<Visit> findByPetIdIn(Collection<Integer> petIds) {
        return find("petId in ?1", petIds).list();
    }
}

