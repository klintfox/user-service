# Microservicio de Creación y Consulta de Usuarios

Este proyecto es un microservicio desarrollado utilizando **SpringBoot 2.5.14** y **Gradle 7.4**. El objetivo del microservicio es permitir la creación y consulta de usuarios, con funcionalidad para validar campos de entrada, manejo de excepciones y generación de tokens JWT.

## Requisitos

- **Java 8 u 11**: Debe utilizarse una versión de Java 8 o 11. Se debe usar al menos dos características propias de estas versiones.
- **SpringBoot 2.5.14 / Gradle 7.4**.
- **Pruebas unitarias**: Cobertura mínima del 80%, utilizando **Spock Framework** o **JUnit**.
- **H2 Database**: Para persistir los usuarios.
- **Formato JSON**: Todos los endpoints deben aceptar y retornar solo JSON, incluso en los mensajes de error. Deben retornar el código HTTP correspondiente y manejar las excepciones adecuadamente.
- **Autenticación con JWT**: El proyecto debe usar JWT para la autenticación.

## Endpoints

### 1. `/sign-up` (Creación de Usuario)

**Método:** `POST`

**Descripción:** Crea un nuevo usuario. Si el usuario ya existe, devuelve un error.

**Cuerpo de la solicitud:**

```json
{
  "name": "String",
  "email": "String",
  "password": "String",
  "phones": [
    {
      "number": long,
      "citycode": int,
      "contrycode": String
    }
  ]
}
```

## Solución