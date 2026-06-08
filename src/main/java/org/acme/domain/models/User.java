package org.acme.domain.models;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    Long id;
    String email;
    String name;
    String phone;
    String password;
    @JsonProperty("tax_id")
    String taxId;
    OffsetDateTime createdAt;;
    List<Address> addresses;
}
