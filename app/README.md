# Agencia de Viajes 

Este es un proyecto de una aplicación de agencia de viajes que gestiona información de hoteles, vuelos, usuarios y reservas. El objetivo es proporcionar una plataforma para la gestión de viajes, con funcionalidades como la creación, modificación y eliminación de hoteles, vuelos y reservas.

## Índice

- [Descripción](#descripción)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Requisitos previos](#requisitos-previos)
- [Instalación](#instalación)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Cómo utilizar](#cómo-utilizar)
- [Pruebas unitarias](#pruebas-unitarias)

## Descripción

Este proyecto es una plataforma de gestión para una agencia de viajes, donde puedes manejar los siguientes aspectos:

- **Hoteles**: Crear, editar, listar y eliminar hoteles.
- **Vuelos**: Crear, editar, listar y eliminar vuelos.
- **Usuarios**: Gestionar usuarios que realizan las reservas.
- **Reservas**: Los usuarios pueden hacer reservas para los vuelos y hoteles disponibles.

## Tecnologías utilizadas

- **Spring Boot**: Framework principal para la aplicación.
- **JPA/Hibernate**: Para el manejo de la base de datos.
- **MySQL**: Base de datos relacional donde se almacenan los datos de hoteles, vuelos, usuarios y reservas.
- **JUnit**: Framework para realizar pruebas unitarias.
- **Mockito**: Biblioteca para la simulación de objetos en las pruebas.

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener los siguientes requisitos:

- **Java 17+** o superior.
- **Maven** (para la gestión de dependencias y ejecución del proyecto).
- **MySQL**: Asegúrate de tener una base de datos MySQL configurada.

## Instalación

Sigue estos pasos para instalar y ejecutar el proyecto:

1. Clona el repositorio:

   ```bash
   git clone https://github.com/luciahernandezdev/HernandezLucia_pruebatec4

2. Configura tu base de datos MySQL y asegúrate de tener la siguiente estructura en tu archivo application.properties:
  ```
   spring.application.name=app
   spring.datasource.url=jdbc:mysql://localhost:3306/agencia?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=klander
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

   # Configuracion de JPA
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

   #Swagger y Swagger UI
   springdoc.api-docs.enabled=true
   springdoc.swagger-ui.enabled=true

   #La ruta a donde estara tu documentacion
   springdoc.swagger-ui.path=/doc

  ```
3. El archivo pom.xml contiene las dependencias necesarias para el proyecto, como Spring Boot, JPA, MySQL, Lombok, Swagger, entre otras:
  ```
  <?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.4.1</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.app</groupId>
	<artifactId>app</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>app</name>
	<description>Demo project for Spring Boot</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>17</java.version>
	</properties>
	<dependencies>
		
		<!-- Dependencia de anotaciones RESTFull -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<!-- Dependencia de Herramientas de Desarrollo -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>

		<!-- Dependencia de Lombok -->
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>

		<!-- Dependencia de Spring Junit5 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- Dependencia de Spring Data JPA -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<!-- Conector de MySQL -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
			<version>8.0.33</version>
		</dependency>

		<!-- Dependencia para Swagger -->
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.3.0</version>
		</dependency>

		<!-- Dependencia para Spring Security -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

		<!-- Dependencia para Mockito -->
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter-api</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-core</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-junit-jupiter</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>

			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<mainClass>com.example.app</mainClass> <!-- Reemplaza esto con la clase principal correcta -->
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>
		</plugins>
	</build>


</project>
  
   ```
## Estructura del proyecto

agencia
│
├── src/main/java/com/example/app
│   ├── config              # Configuraciones generales de la aplicación
│   │   └── AppConfig.java  # Configuración general (por ejemplo, de CORS, seguridad)
│   ├── controllers        # Controladores REST
│   ├── dtos              # Objetos de transferencia de datos (DTOs)
│   ├── entities          # Entidades de la base de datos
│   ├── repositories      # Repositorios para acceder a la base de datos
│   ├── services          # Lógica de negocio
│   ├── AppApplication.java  # Clase principal de la aplicación
│
├── src/test/java/com/example/app
│   ├── services         # Tests de los servicios
│
├── src/main/resources
│   ├── application.properties  # Configuración del proyecto
└── pom.xml              # Dependencias del proyecto

## Como Utilizar
Cómo utilizar
La aplicación expone varias rutas RESTful para interactuar con los datos:

- GET /hoteles: Obtiene la lista de todos los hoteles.

- POST /hoteles: Crea un nuevo hotel.

- PUT /hoteles/{id}: Modifica la información de un hotel existente.

- DELETE /hoteles/{id}: Elimina un hotel.

- GET /vuelos: Obtiene la lista de vuelos disponibles.

- POST /vuelos: Crea un nuevo vuelo.

- PUT /vuelos/{id}: Modifica la información de un vuelo existente.

- DELETE /vuelos/{id}: Elimina un vuelo.

- GET /usuarios: Obtiene la lista de todos los usuarios.

- POST /usuarios: Crea un nuevo usuario.

- PUT /usuarios/{id}: Modifica la información de un usuario.

- DELETE /usuarios/{id}: Elimina un usuario.

- GET /reservas: Obtiene la lista de reservas.

- POST /reservas: Crea una nueva reserva para un vuelo y/o hotel.

## Pruebas Unitarias
Se han implementado pruebas unitarias utilizando JUnit y Mockito.

 1. Se envía solicitud de listado de todos los hoteles registrados.
   - Si hay hoteles registrados: Permite continuar con normalidad y muestra listado completo.  
   - Si no hay hoteles: Notifica la no existencia mediante una excepción.