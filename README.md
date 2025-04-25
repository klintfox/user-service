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

## Requisitos
- Java 11
- Gradle 7.4
- Lombok

### Despligue
1. Clona este repositorio:

```bash
git clone https://github.com/klintfox/user-service.git
cd user-service
```
   
2 Asegúrate de tener Gradle instalado. Si no lo tienes, puedes instalarlo desde aquí.

3 Para construir el proyecto ubicarse en la ruta dentro del repositorio 'service-users', ejecuta el siguiente comando en la raíz del proyecto:

```sh
gradlew build  (windows)
```

4 Ejecución del proyecto
```sh
./gradlew bootRun
```

5 Pruebas
Este proyecto incluye pruebas unitarias y de integración utilizando JUnit y Mockito. Para ejecutar las pruebas, usa el siguiente comando:

```bash
gradlew test
```

6 Además, para generar un informe de cobertura de pruebas utilizando JaCoCo, ejecuta el siguiente comando:

```bash
./gradlew jacocoTestReport
```

### Diagramas

**Diagrama de Componente**

![DIAGRAMA COMPONENTE](https://github.com/klintfox/user-service/blob/main/captures/DC.PNG)

**Diagrama de Secuencia**

![DIAGRAMA DE SECUENCIA](https://github.com/klintfox/user-service/blob/main/captures/DS.PNG)

### Postman

**Registro de Usuario**
![Registro de Usuario](https://github.com/klintfox/user-service/blob/main/captures/1.PNG)

**Consulta de Usuario**
![Consulta de Usuario](https://github.com/klintfox/user-service/blob/main/captures/2.PNG)

### Validaciones

**Validación - Usuario ya Registrado**
![Validación - Usuario ya Registrado](https://github.com/klintfox/user-service/blob/main/captures/3.PNG)

**Validación - Formato de Correo**
![Validación - Formato de Correo](https://github.com/klintfox/user-service/blob/main/captures/4.PNG)

**Validación - Formato de la Contraseña**
![Validación - Formato de la Contraseña](https://github.com/klintfox/user-service/blob/main/captures/5.PNG)

### Cobertura

**Cobertura**
![JACOCO COVERAGE](https://github.com/klintfox/user-service/blob/main/captures/JACOCO.PNG)

**Resumen de Test**
![TEST](https://github.com/klintfox/user-service/blob/main/captures/TEST.PNg)