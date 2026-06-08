package org.acme.infraestructure.input.mappers;

import java.util.stream.Collectors;
import org.acme.domain.models.Address; 
import org.acme.domain.models.User;
import org.acme.infraestructure.input.dtos.UserDtoModel;

public class DtoToDomain {

    public static User toDomain(UserDtoModel dto) {
        User user = new User();
        user.setId(dto.id);
        user.setEmail(dto.email);
        user.setName(dto.name);
        user.setPhone(dto.phone);
        user.setPassword(dto.password);
        user.setTaxId(dto.taxId);
        user.setCreatedAt(dto.createdAt);

        if (dto.addresses != null) {
            user.setAddresses(dto.addresses.stream()
                    .map(addrDto -> {

                        Address addrDomain = new Address();
                        addrDomain.setId(addrDto.getId());
                        addrDomain.setName(addrDto.getName());
                        addrDomain.setStreet(addrDto.getStreet());
                        addrDomain.setCountryCode(addrDto.getCountryCode());
                        return addrDomain;
                    })
                    .collect(Collectors.toList()));
        }

        return user;
    }
}