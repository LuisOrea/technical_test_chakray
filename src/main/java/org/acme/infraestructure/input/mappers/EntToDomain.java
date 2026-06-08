package org.acme.infraestructure.input.mappers;

import java.util.List;

import org.acme.domain.models.Address;
import org.acme.domain.models.User;
import org.acme.infraestructure.output.entity.UserEntity;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntToDomain {

    public User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.id);
        user.setName(entity.name);
        user.setEmail(entity.email);
        user.setPhone(entity.phone);
        user.setPassword(entity.password);
        user.setTaxId(entity.taxId);
        user.setCreatedAt(entity.createdAt);

        if (entity.addresses != null) {
            List<Address> domainAddresses = entity.addresses.stream().map(addEntity -> {
                Address address = new Address();
                address.setId(addEntity.id);
                address.setName(addEntity.name);
                address.setStreet(addEntity.street);
                address.setCountryCode(addEntity.countryCode);
                return address;
            }).toList();
            user.setAddresses(domainAddresses);
        }
        return user;
    }
}
