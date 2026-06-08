package org.acme.infraestructure.input.mappers;

import java.util.stream.Collectors;
import org.acme.domain.models.User;
import org.acme.infraestructure.input.dtos.UserResponseDto;
import org.acme.infraestructure.input.dtos.AddressDtoModel;

public class DomainToDto {

    public static UserResponseDto fromDomain(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = user.getId();
        dto.email = user.getEmail();
        dto.name = user.getName();
        dto.phone = user.getPhone();
        dto.taxId = user.getTaxId();
        dto.createdAt = user.getCreatedAt();

        if (user.getAddresses() != null) {
            dto.addresses = user.getAddresses().stream()
                    .map(addrDomain -> {
                        AddressDtoModel addrDto = new AddressDtoModel();
                        addrDto.setId(addrDomain.getId());
                        addrDto.setName(addrDomain.getName());
                        addrDto.setStreet(addrDomain.getStreet());
                        addrDto.setCountryCode(addrDomain.getCountryCode());
                        return addrDto;
                    })
                    .collect(Collectors.toList());
        }
        return dto;
    }
}