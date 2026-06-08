package org.acme.infraestructure.input;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class UserResourceIT {

    @Test
    public void testPatchUser_IntegrityCheck() {
        String patchBody = "{ \"name\": \"Carlos Actualizado\" }";

        // 2. Ejecutamos el PATCH
        given()
            .contentType(ContentType.JSON)
            .body(patchBody)
        .when()
            .patch("/users/1") 
        .then()
            .statusCode(200)
            .body("name", is("Carlos Actualizado"))
            
            .body("created_at", notNullValue())
            .body("created_at", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[+-]\\d{2}:\\d{2}"));
    }

    @Test
    public void testLogin_Unauthorized_WhenInvalidCredentials() {
        given()
            .contentType(ContentType.JSON)
            .body("{ \"tax_id\": \"INVALID\", \"password\": \"wrong\" }")
        .when()
            .post("/users/login")
        .then()
            .statusCode(401);
    }
}