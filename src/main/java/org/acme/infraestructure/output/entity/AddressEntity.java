package org.acme.infraestructure.output.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity extends PanacheEntity {
    @Column(name = "address_name")
    public String name;

    @Column(name = "street_name")
    public String street;

    @Column(name = "country_code")
    public String countryCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserEntity user;
}
