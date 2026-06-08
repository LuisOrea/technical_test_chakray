package org.acme.infraestructure.input.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDtoModel {
    private Long id;
    private String name;
    private String street;
    @JsonProperty("country_code")
    private String countryCode;
}
