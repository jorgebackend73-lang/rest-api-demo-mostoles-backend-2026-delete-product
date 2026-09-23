package com.example.controllers;

import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.entities.Presentation;
import com.example.entities.Product;
import com.example.services.ProductService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;

import tools.jackson.databind.ObjectMapper;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(ProductController.class)
/* La anotación anterior es la recomendada para implementar test de integración,
a la capa de controladores que conlleva hacer peticiones HTTP.

Esta anotación no sirve si tenemos implementado Spring Security pq no carga
todo el contexto de Spring. Cuando se implemente la seguridad habra que comentar 
esta anotación y usar @SpringBootTest.

Evita usar una buen cantidad de anotaciones, nos las indica cuando ponemos el cursor encima.
 */

@AutoConfigureTestDatabase(replace = Replace.NONE)
/* Anotación para utilizar base de datos real que no sea H2 Database, MySQL por ejemplo y
que al terminar la prueba deje la base de datos tal y como estaba. */

@AutoConfigureMockMvc
/* Esta anotación es para realizar peticiones a los end points, lo cual
suministra y configura. */
class ProductControllerTest {

    /* Método que nos permite usar todo tipo de vervos o métodos y peticiones de Spring. */
    @Autowired 
    /* para meter dependencias por tipo sin tirar de Lombock que complicaría la cosa. */

    /* Mockito Bean simula todas las dependencias del controller simuladas */
    MockMvc mockMvc;

    /*metemos las dependencias del controller OG */
    @MockitoBean 
    ProductService productService;

    @MockitoBean 
    FileUploadUtil fileUploadUtil;

    @MockitoBean 
    FileDownloadUtil fileDownloadUtil;

    @Autowired 
    ObjectMapper objectMapper;

    @Test 
    @DisplayName("Controller Test que recupera todos los productos")
    void testFindAll() {

        // given
        List<Product> products = new ArrayList<>();

        Presentation presentation1 = Presentation.builder()
            .name("decenas")
            .description("Por decenas")
            .build();

        Product product1 = Product.builder()
            .name("Cámara")
            .description("HP Cámara")
            .price(new BigDecimal(500))
            .stock(1900)
            .productImage(null)
            .presentation(presentation1)
            .build();

        Presentation presentation2 = Presentation.builder()
            .name("unidades")
            .description("Por unidades")
            .build();

        Product product2 = Product.builder()
            .name("Frigorífico")
            .description("General Electric")
            .price(new BigDecimal(2500))
            .stock(3900)
            .productImage(null)
            .presentation(presentation2)
            .build();

        products.add(product1);
        products.add(product2);

        /*Al hacer una petición al end point nos dará la lista de productos que hemos
        preparado arriba. */

        given(productService.findAll(Sort.by("name")))
            .willReturn(products);

        /*Con lo anterior tenemos preparado el caso. */

        // when => realizar la petición (request) HTTP, mediante el metodo GET 
        // al end point de products ("/products").

        // MockMvc es el que simula las peticiones a HTTP.
        try {
            ResultActions response = mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON));
            // la respuesta queda en la variable response

        // then Con $ accedes a las propiedades del .json a las que queremos pedirles el .size
        response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.productos.size()",
            is(products.size())));
        

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        

        

    }
    
}
