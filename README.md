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

-	Donde el correo debe seguir una expresión regular para validar que formato sea el correcto. (aaaaaaa@undominio.algo), si no cumple enviar mensaje de error.
-	La clave debe seguir una expresión regular para validar que formato sea el correcto. Debe tener solo una Mayúscula y solamente dos números (no necesariamente consecutivos), en combinación de letras minúsculas, largo máximo de 12 y mínimo 8. "a2asfGfdfdf4", si no cumple enviar mensaje de error.
-	El nombre y los teléfonos son campos opcionales.
-	En caso de éxito, retornar el usuario y los siguientes campos:
	○	id: id del usuario (puede ser lo que se genera por el banco de datos, pero sería más deseable un UUID)
	○	created: fecha de creación del usuario
	○	lastLogin: del último ingreso
	○	token: token de acceso de la API (debe utilizar JWT)
	○	isActive: Indica si el usuario sigue habilitado dentro del sistema.
-	El usuario debe ser persistido en una BD utilizando spring data (debe utilizar H2). En caso de la contraseña, sería ideal que pudiese ser encriptada.
-	Si el usuario ya existe, debe enviar mensaje de error indicando que ya existe.

**En caso de error de un endpoint debe retornar:**

```json
{
"error": [{
"timestamp": Timestamp,
"codigo": int,
"detail": String
}]
}
```

### Obligatorio /login:

Endpoint el cual será para consultar el usuario, para ello debe utilizar el token generado en el endpoint anterior para realizar la consulta y así retornar toda la información del usuario persistido, considere que el token debe cambiar al ejecutar por lo que se actualizará el token.

**El contrato de salida debe ser:**
```json
{
	"id": "e5c6cf84-8860-4c00-91cd-22d3be28904e",
	"created": "Nov 16, 2021 12:51:43 PM",
	"lastLogin": "Nov 16, 2021 12:51:43 PM",
	"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpb0B0ZXN0...",
	"isActive": true,
	"name": "Julio Gonzalez",
	"email": "julio@testssw.cl",
	"password": "a2asfGfdfdf4",
	"phones": [
		{
			"number": 87650009,
			"citycode": 7,
			"contrycode": "25"
		}
	]
}
```

## Solución

## Requisitos
- Java 11
- Gradle 7.4
- Lombok

**nota**
Todos los comandos gradle son para windows

### Despligue
1. Clona este repositorio:

```bash
git clone https://github.com/klintfox/user-service.git
cd user-service
```
   
2 Asegúrate de tener Gradle y Lombok instalado.

3 Para construir el proyecto ubicarse en la ruta dentro del repositorio 'service-users', ejecuta el siguiente comando en la raíz del proyecto:

```sh
gradlew build
```

4 Ejecución del proyecto
```sh
gradlew bootRun
```

5 Pruebas
Este proyecto incluye pruebas unitarias y de integración utilizando JUnit y Mockito. Para ejecutar las pruebas, usa el siguiente comando:

```bash
gradlew test
```

6 Además, para generar un informe de cobertura de pruebas utilizando JaCoCo, ejecuta el siguiente comando:

```bash
gradlew jacocoTestReport
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
![TEST RESUME](https://github.com/klintfox/user-service/blob/main/captures/TESTS.PNG)