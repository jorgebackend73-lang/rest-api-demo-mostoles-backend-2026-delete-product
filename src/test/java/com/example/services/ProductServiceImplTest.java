package com.example.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.dao.PresentationDao;
import com.example.dao.ProductDao;
import com.example.entities.Presentation;
import com.example.entities.Product;

import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.Assertions.*;


/* Test de la capa de servicios. Hay que simular las dependencias de la capa DAO
y para ello debemos usar Mokito. */
/* Importante anotación @WebMvcTest por no tener implementada la seguridad. 
TODO: cambiar cuando se implemente spring security con Jason Web Token. 

Al final no va aquí, este comentario es para la capa de Controladores
*/

/* Los test hay que realizarlos en aislamiento. Los test a la capa de servicio
ya se consideran test de integración pq dependen de la capa de repositorio,
pero dichas dependencias se simulan (mock) en lugar de inyectarlas realmente 
y aquí entra el framework Mockito */

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    /* La dependencia @Mock simula las dependencias en lugar de inyectarlas realmente,
    para aislar todo lo posible el test q se esta implementando. */
    private ProductDao productDao;

    @Mock 
    private PresentationDao presentationDao;

    @InjectMocks 
    private ProductServiceImpl productServiceImpl;

    Product product1, product2;

    List<Product> productsList = new ArrayList<>();

    /* */
    @BeforeEach 
    void setUp() {

        Presentation presentation = Presentation.builder()
            .name("unidades")
            .description("po unidades")
            .build();

        product1 = Product.builder()
            .name("Google Pixel 7")
            .description("Telefono de Google")
            .price(new BigDecimal(400))
            .stock(1000)
            .productImage(null)
            .presentation(presentation)
            .build();

        product2 = Product.builder()
            .name("iPhone 17 Pro")
            .description("Telefono de Apple")
            .price(new BigDecimal(1300))
            .stock(1500)
            .productImage(null)
            .presentation(presentation)
            .build();

        productsList.add(product1);
        productsList.add(product2);

    }

    @Test
    void testDelete() {

    }

    @Test
    void testFindAll() {

    }

    @Test
    void testFindAll2() {

    }

    @Test
    void testFindAll3() {

    }

    @Test
    void testFindById() {

    }

    @Test
    @DisplayName("Test del servicio para peristir un producto")
    void testSave() {
    /* Necesitamos crear productos para todos los test de la capa de servicios */

    //Beheaviour Driven Development

    // given. Dado que se persise un producto. Caso de prueba.
    given(productDao.save(product1)).willReturn(product1);

    // when. Cuando se guarde el producto utilizando el servicio
    Product productoGuardado = productServiceImpl.save(product1);
        // como esta todo simulado hay que hacerlo todo a mano.    

    // then. Resultado esperado.
    assertThat(productoGuardado).isNotNull();
    

    }
}
