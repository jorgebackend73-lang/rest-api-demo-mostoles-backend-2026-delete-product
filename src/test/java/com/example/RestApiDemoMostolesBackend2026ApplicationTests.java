package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestApiDemoMostolesBackend2026ApplicationTests {

	// Esta clase nos levanta todo el contexto de spring, se crea sola en la parte test del proyecto.
	// Con [ mvn test ] en la terminal abierta desde el directorio del proyecto lanzamos todos los test.
	// Para ejecutar un test en concreto [ mvn test -Dtest=NombreClase#nombreDelTest ]
	// En nuestro caso [ mvn test -Dtest=ProductDaoTest#testSaveProduct <- metodo implementado ]
	@Test
	@DisplayName("Test de carga del contexto de spring")
	void contextLoads() {
	}

}
