package org.acme.infraestructure.input.dtos;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoModel {
    public Long id;

    public String email;

    public String name;

    public String phone;

    public String password;

    @JsonProperty("tax_id")
    public String taxId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Indian/Antananarivo")
    @JsonProperty("created_at")

    public OffsetDateTime createdAt;;

    public List<AddressDtoModel> addresses;
}
