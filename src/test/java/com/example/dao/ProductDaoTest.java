package com.example.dao;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;

import com.example.entities.Presentation;
import com.example.entities.Product;

/* Anotación para probar solo entidades y repositorios de datos.
No todo el contexto de Spring.*/
@DataJpaTest

/* Anotación para que al terminar haga roll-back y  deje la base de datos 
igual que como estaba. Asi no hace falta que los metodos sean transactional, 
está incluido por defecto. */
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductDaoTest {

    /* @Autowired para crear beans para los objetos e interfaces siguientes, 
    pues lombok no está presente en el testeo */
    @Autowired 
    private PresentationDao presentationDao;

    @Autowired
    private ProductDao productDao;

    private Presentation presentacionPorUnidades;
    private Presentation presentacionPorDecenas;

    private Product product0;

    /* Antes de probar a persistir un producto hay que crear el producto. Esto será necesario para
    todos los métodos de prueba, por ello lo vamos a hacer fuera, en un método Set-Up, anotado para
    que se ejecute antes de cualquier otro metodo @BeforeEach. */
    @BeforeEach
    void setUp() {

        presentacionPorUnidades = Presentation.builder()
            .name("unidad")
            .description("por unidades")
            .build();

        presentacionPorDecenas = Presentation.builder()
            .name("decenas")
            .description("por decenas")
            .build();



    }

    @Test
    @DisplayName("Test para probar, agregar o persistir.")
    void testSaveProduct() {

    // Given
    Presentation presentation0 = presentationDao.save(presentacionPorUnidades);

    product0 = Product.builder()
        .name("Google Pixel 11 Pro")
        .description("Google Smart Phone")
        .price(new BigDecimal(900))
        .presentation(presentation0)
        .build();

    // When
    Product productoGuardado = productDao.save(product0);

    // Then
    assertThat(productoGuardado).isNotNull();
    assertThat(productoGuardado.getId());

    }

}
