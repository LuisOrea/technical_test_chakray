package org.acme.infraestructure.output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acme.domain.models.User;
import org.acme.domain.ports.output.UserRepositoryPort;
import org.acme.infraestructure.input.mappers.EntToDomain;
import org.acme.infraestructure.output.entity.AddressEntity;
import org.acme.infraestructure.output.entity.UserEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserEntity, Long>, UserRepositoryPort {

    @Inject
    EntToDomain mapper;

    @Transactional
    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();

        entity.email = user.getEmail();
        entity.name = user.getName();
        entity.phone = user.getPhone();
        entity.password = user.getPassword();
        entity.taxId = user.getTaxId();
        entity.createdAt = user.getCreatedAt();

        if (user.getAddresses() != null) {
            entity.addresses = user.getAddresses().stream().map(domainAdd -> {
                AddressEntity addEntity = new AddressEntity();
                addEntity.name = domainAdd.getName();
                addEntity.street = domainAdd.getStreet();
                addEntity.countryCode = domainAdd.getCountryCode();
                addEntity.user = entity;
                return addEntity;
            }).toList();
        }

        entity.persist();

        UserEntity.flush();



        return mapper.toDomain(entity);

    }

    @Transactional
    @Override
    public User update(User user) {
        UserEntity entity = UserEntity.findById(user.getId());

        entity.email = user.getEmail();
        entity.name = user.getName();
        entity.phone = user.getPhone();
        entity.password = user.getPassword();
        entity.taxId = user.getTaxId();

        if (user.getAddresses() != null) {

            user.getAddresses().forEach(domainAdd -> {

                AddressEntity existente = entity.addresses.stream()
                        .filter(a -> a.id != null && a.id.equals(domainAdd.getId()))
                        .findFirst()
                        .orElse(null);

                if (existente != null) {
                    existente.name = domainAdd.getName();
                    existente.street = domainAdd.getStreet();
                    existente.countryCode = domainAdd.getCountryCode();

                    existente.persist();
                } else {

                    AddressEntity nueva = new AddressEntity();
                    nueva.name = domainAdd.getName();
                    nueva.street = domainAdd.getStreet();
                    nueva.countryCode = domainAdd.getCountryCode();
                    nueva.user = entity; 
                    entity.addresses.add(nueva);
                }
            });
        }

        user.setId(entity.id);

        return user;

    };

    @Override
    public User findUserId(Long id) {
        UserEntity entity = this.findById(id);

        if (entity == null) {
            return null;
        }

        return mapper.toDomain(entity);

    };

    @Override
    public List<User> getUserFilter(String sortedBy, String attribute, String operator, String value) {
        StringBuilder query = new StringBuilder("FROM UserEntity u");
        Map<String, Object> params = new HashMap<>();

        Map<String, String> fieldMap = Map.of(
                "name", "name",
                "email", "email",
                "phone", "phone",
                "tax_id", "taxId",
                "created_at", "created_at");

        if (attribute != null && !fieldMap.containsKey(attribute)) {
            throw new IllegalArgumentException("Campo de filtro no válido: " + attribute);
        }

        if (attribute != null && operator != null && value != null) {
            String entityField = fieldMap.getOrDefault(attribute, attribute);
            query.append(" WHERE u.").append(entityField).append(" ");

            switch (operator) {
                case "eq" -> {
                    query.append("= :value");
                    params.put("value", value);
                }
                case "co" -> {
                    query.append("LIKE :value");
                    params.put("value", "%" + value + "%");
                }
                case "sw" -> {
                    query.append("LIKE :value");
                    params.put("value", value + "%");
                }
                case "ew" -> {
                    query.append("LIKE :value");
                    params.put("value", "%" + value);
                }
                default -> throw new IllegalArgumentException("Operador no soportado");
            }
        }

        if (sortedBy != null && !sortedBy.isBlank()) {
            String entitySortedBy = fieldMap.getOrDefault(sortedBy, sortedBy);
            query.append(" ORDER BY u.").append(entitySortedBy).append(" ASC");
        }

        List<UserEntity> entities = params.isEmpty() ? this.list(query.toString())
                : this.list(query.toString(), params);

        return entities.stream().map(entity -> mapper.toDomain(entity)).toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.deleteById(id);
    };

    @Override
    public User findByTaxId(String taxId) {
        UserEntity user = find("taxId", taxId).firstResult();

        if (user == null) return null;

        return mapper.toDomain(user);
    }

}
