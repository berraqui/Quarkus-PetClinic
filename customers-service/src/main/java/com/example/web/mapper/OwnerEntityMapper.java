package com.example.web.mapper;



import com.example.model.Owner;
import com.example.web.OwnerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class OwnerEntityMapper implements Mapper<OwnerRequest, Owner> {
    // This is done by hand for simplicity purpose. In a real life use-case we should consider using MapStruct.
    @Override
    public Owner map(final Owner owner, final OwnerRequest request) {
        owner.address = request.address();
        owner.city = request.city();
        owner.telephone = request.telephone();
        owner.firstName = request.firstName();
        owner.lastName = request.lastName();
        return owner;
    }
}