package org.acme.application.services;

import static org.junit.jupiter.api.Assertions.*;

import org.acme.applications.services.GetUsers;
import org.acme.domain.models.User;
import org.acme.domain.ports.output.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;

public class GetUsersTest {

    @Test
    public void testGetUsers_WithFilter_ShouldParseAndCallRepository() {
        FakeRepository repo = new FakeRepository();
        GetUsers service = new GetUsers(repo);

        service.getUsers("name", "status+eq+active");

       
        assertEquals("name", repo.capturedSortedBy);
        assertEquals("status", repo.capturedAttribute);
        assertEquals("eq", repo.capturedOperator);
        assertEquals("active", repo.capturedValue);
    }

    @Test
    public void testGetUsers_NoFilter_ShouldPassNulls() {
        FakeRepository repo = new FakeRepository();
        GetUsers service = new GetUsers(repo);

        service.getUsers("name", "");

        assertNull(repo.capturedAttribute);
        assertNull(repo.capturedOperator);
    }

    @Test
    public void testGetUsers_InvalidFilter_ShouldThrowException() {
        GetUsers service = new GetUsers(new FakeRepository());

        assertThrows(IllegalArgumentException.class, () -> {
            service.getUsers("name", "mal_formato");
        });
    }

    private static class FakeRepository implements UserRepositoryPort {
        String capturedSortedBy, capturedAttribute, capturedOperator, capturedValue;

        public List<User> getUserFilter(String s, String a, String o, String v) {
            this.capturedSortedBy = s;
            this.capturedAttribute = a;
            this.capturedOperator = o;
            this.capturedValue = v;
            return Collections.emptyList();
        }

        public User save(User u) { return null; }
        public void delete(Long id) {}
        public User update(User u) { return null; }
        public User findByTaxId(String taxId) { return null; }
        public User findUserId(Long id) { return null; }
    }
}