# Technical Test - User Management API

API REST construida con Quarkus para la gestión de usuarios, cumpliendo con los requisitos de persistencia, validación y empaquetado.

## Requisitos previos

1. Docker instalado.
2. Maven (opcional, solo si deseas compilar desde cero).

## Cómo ejecutar el proyecto

### Opción 1: Usando la imagen Docker (Recomendado)

1. Construir la imagen:
```bash
docker build -f src/main/docker/Dockerfile.jvm -t acme/technical-test .
```

2. Correr el contenedor:
```bash
docker run -i --rm -p 8080:8080 acme/technical-test
```

La API estará disponible en `http://localhost:8080`

### Opción 2: Desarrollo local

1. Compilar y empaquetar:
```bash
./mvnw clean package
```

2. Ejecutar en modo desarrollo:
```bash
./mvnw quarkus:dev
```

## Persistencia

Este proyecto utiliza una base de datos H2 en memoria para facilitar las pruebas.

- **Modo:** `drop-and-create` (la base de datos se limpia cada vez que el contenedor inicia).
- **Consola H2:** `http://localhost:8080/h2-console`
  - URL: `jdbc:h2:mem:testdb`
  - Usuario: `sa`
  - Contraseña: _(vacía)_

## Pruebas

El proyecto incluye pruebas unitarias con JUnit y Mockito. Para ejecutarlas:
```bash
./mvnw test
```

## Documentación de la API

Swagger UI disponible en: `http://localhost:8080/q/swagger-ui/`

## Endpoints

### POST `/login`
Autentica un usuario usando `tax_id` como username.

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"tax_id": "CATC900101ABC", "password": "shjdu2378sanj"}'
```

---

### GET `/users`
Lista todos los usuarios. Soporta ordenamiento y filtrado.

**Ordenar por atributo** — valores válidos: `email | id | name | phone | tax_id | created_at`
```bash
curl "http://localhost:8080/users?sortedBy=name"
```

**Filtrar** — operadores: `co` (contains) · `eq` (equals) · `sw` (starts with) · `ew` (ends with)
```bash
# Nombre que contiene "user"
curl "http://localhost:8080/users?filter=name+co+user"

# Email que termina en "mail.com"
curl "http://localhost:8080/users?filter=email+ew+mail.com"

# Teléfono que empieza con "555"
curl "http://localhost:8080/users?filter=phone+sw+555"

# tax_id exacto
curl "http://localhost:8080/users?filter=tax_id+eq+AARR990101XXX"
```

---

### POST `/users`
Crea un nuevo usuario.

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Juan Pérez",
    "email": "juan@mail.com",
    "password": "1234",
    "phone": "5551234567",
    "taxId": "AARR990101XXX"
  }'
```

---

### PATCH `/users/{id}`
Actualiza uno o más atributos de un usuario por Id.

```bash
curl -X PATCH http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Juan Actualizado"}'
```

---

### DELETE `/users/{id}`
Elimina un usuario por Id.

```bash
curl -X DELETE http://localhost:8080/users/1
```

Link Github Repositorio: https://github.com/LuisOrea/technical_test_chakray.git