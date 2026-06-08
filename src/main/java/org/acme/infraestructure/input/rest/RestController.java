package org.acme.infraestructure.input.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.applications.services.CreateUser;
import org.acme.applications.services.DeleteUser;
import org.acme.applications.services.GetUsers;
import org.acme.applications.services.LoginUser;
import org.acme.applications.services.PatchUser;
import org.acme.domain.models.User;
import org.acme.infraestructure.input.dtos.LoginDto;
import org.acme.infraestructure.input.dtos.UserDtoModel;
import org.acme.infraestructure.input.dtos.UserResponseDto;
import org.acme.infraestructure.input.mappers.DomainToDto;
import org.acme.infraestructure.input.mappers.DtoToDomain;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@Path("/users")
@AllArgsConstructor
@Tag(name = "User API", description = "Operaciones de usuarios")
public class RestController {

    private final CreateUser createService;
    private final PatchUser patchService;
    private final DeleteUser deleteService;
    private final GetUsers getService;
    private final LoginUser loginService;

    @GET
    @Operation(summary = "Obtener lista de usuarios", description = "Retorna una lista filtrada y/o ordenada de usuarios registrados.")
    @APIResponse(responseCode = "200", description = "Lista de usuarios retornada exitosamente")
    public Response getUsers(
            @Parameter(description = "Campo por el cual ordenar los usuarios") @QueryParam("sortedBy") String sortedBy,
            @Parameter(description = "Filtro aplicado a la búsqueda") @QueryParam("filter") String filter) {

        List<User> users = getService.getUsers(sortedBy, filter);

        List<UserResponseDto> response = users.stream()
                .map(DomainToDto::fromDomain)
                .collect(Collectors.toList());

        return Response.ok(response).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Registrar nuevo usuario", description = "Crea un nuevo usuario en el sistema y retorna el modelo creado.")
    @APIResponse(responseCode = "201", description = "Usuario creado exitosamente")
    public Response save(UserDtoModel userDto) {

        User userDomain = DtoToDomain.toDomain(userDto);

        User saveUser = createService.create(userDomain);

        return Response.status(Response.Status.CREATED)
                .entity(DomainToDto.fromDomain(saveUser))
                .build();
    }

    @PATCH
    @Path("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los campos de un usuario existente identificado por ID.")
    @APIResponse(responseCode = "200", description = "Usuario actualizado exitosamente")
    public Response patch(@Parameter(description = "ID del usuario a actualizar") @PathParam("id") Long id,
            UserDtoModel patchDto) {
        User patchUserDomain = DtoToDomain.toDomain(patchDto);

        User updatedUser = patchService.patch(id, patchUserDomain);

        UserResponseDto responseDto = DomainToDto.fromDomain(updatedUser);

        return Response.ok(responseDto).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema mediante su ID.")
    @APIResponse(responseCode = "204", description = "Usuario eliminado correctamente")
    public Response delete(@Parameter(description = "ID del usuario a eliminar") @PathParam("id") Long id) {
        deleteService.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/login")
    @Operation(summary = "Autenticación de usuario", description = "Valida las credenciales (tax_id y password) para iniciar sesión.")
    @APIResponse(responseCode = "200", description = "Login exitoso")
    @APIResponse(responseCode = "401", description = "Credenciales incorrectas")
    public Response login(LoginDto request) {
        boolean isAuthenticated = loginService.authenticate(request.tax_id, request.password);

        if (isAuthenticated) {
            return Response.ok("Login exitoso").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
