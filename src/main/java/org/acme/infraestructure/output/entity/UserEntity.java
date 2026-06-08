package org.acme.infraestructure.output.entity;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends PanacheEntity {

    @Column(name = "email_user", nullable = false)
    public String email;

    @Column(name = "name_user")
    public String name;

    @Column(name = "phone_user")
    public String phone;

    @Column(name = "password_user")
    @JsonIgnore
    public String password;

    @Column(name = "tax_id", unique = true, nullable = false)
    public String taxId;

    @Column(name = "created_at", nullable = false)
    public OffsetDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<AddressEntity> addresses;
}
