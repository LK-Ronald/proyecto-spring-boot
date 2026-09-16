# Users Management — Spring Boot, Arquitectura Hexagonal y DDD

Aplicación de **gestión de usuarios** construida con **Java 24** y **Spring Boot 3.4.3**, aplicando **Arquitectura Hexagonal** y **DDD**.

## Ejecutar en terminal

### 1) Instalar dependencias y correr pruebas

```bash
./mvnw clean test
```

En Windows:

```bat
.\mvnw.cmd clean test
```

### 2) Construir el JAR

```bash
./mvnw clean package
```

El artefacto se genera en `target/users-management-2.1.0.jar`.

### 3) Ejecutar la aplicación

Opción A — con Maven:

```bash
./mvnw spring-boot:run
```

Opción B — ejecutando el JAR:

```bash
java -jar target/users-management-2.1.0.jar
```

Por defecto levanta en `http://localhost:8080`.
