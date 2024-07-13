package com.example.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "pets")
@Data
public class Pet extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Column(name = "name")
    public String name;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    public Date birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    public PetType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    public Owner owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", type=" + type.getName() +
                ", ownerFirstname=" + owner.firstName +
                ", ownerLastname=" + owner.lastName +
                '}';
    }
}

