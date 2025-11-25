package es.etg.daw.dawes.java.rest.restfull;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.Producto;
import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.ProductoId;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.repository.mock.ProductoFactory;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto.ProductoRequest;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto.ProductoResponse;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductoControllerTest {

    public static String ENDPOINT = "/productos";

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private JacksonTester<ProductoRequest> jsonProductoRequest;

    @Autowired
    private JacksonTester<ProductoResponse> jsonProductoResponse;

    @BeforeEach
    public void setUp() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    @Order(1)
    public void getAll() throws Exception {
        int numProductos = ProductoFactory.getDemoData().values().size();

        MockHttpServletResponse response = mockMvc.perform(
            get(ENDPOINT).accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        List<ProductoResponse> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, ProductoResponse.class));

        assertAll(
            () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
            () -> assertTrue(res.size() == numProductos)
        );
    }

    @Test
    @Order(10)
    public void save() throws Exception {
        Producto nuevo = ProductoFactory.create();
        ProductoRequest req = new ProductoRequest(nuevo);

        MockHttpServletResponse response = mockMvc.perform(
            post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonProductoRequest.write(req).getJson())
                    .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        ProductoResponse res = mapper.readValue(response.getContentAsString(), ProductoResponse.class);

        assertAll(
            () -> assertEquals(response.getStatus(), HttpStatus.CREATED.value()),
            () -> assertEquals(res.nombre(), nuevo.getNombre()),
            () -> assertEquals(res.precio(), nuevo.getPrecio()),
            () -> assertEquals(res.categoria(), nuevo.getCategoria().getValue()),
            () -> assertTrue(res.id() > 0)
        );
    }

    @Test
    @Order(11)
    public void saveEmptyNombreShouldFail() throws Exception {
        Producto nuevo = ProductoFactory.create();
        nuevo.setNombre(null);
        ProductoRequest req = new ProductoRequest(nuevo);

        MockHttpServletResponse response = mockMvc.perform(
            post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonProductoRequest.write(req).getJson())
                    .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus())
        );
    }

    @Test
    @Order(20)
    public void update() throws Exception {
        Producto nuevo = ProductoFactory.create();
        nuevo.setId(new ProductoId(1));
        ProductoRequest req = new ProductoRequest(nuevo);

        MockHttpServletResponse response = mockMvc.perform(
            put(ENDPOINT + "/" + nuevo.getId().getValue())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonProductoRequest.write(req).getJson())
                    .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        ProductoResponse res = mapper.readValue(response.getContentAsString(), ProductoResponse.class);

        assertAll(
            () -> assertEquals(response.getStatus(), HttpStatus.OK.value()),
            () -> assertEquals(res.nombre(), nuevo.getNombre()),
            () -> assertEquals(res.precio(), nuevo.getPrecio()),
            () -> assertEquals(res.categoria(), nuevo.getCategoria().getValue()),
            () -> assertEquals(res.id(), nuevo.getId().getValue())
        );
    }

    @Test
    @Order(30)
    public void deletee() throws Exception {
        Producto nuevo = ProductoFactory.create();
        nuevo.setId(new ProductoId(1));
        ProductoRequest req = new ProductoRequest(nuevo);

        MockHttpServletResponse response = mockMvc.perform(
            delete(ENDPOINT + "/" + nuevo.getId().getValue())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonProductoRequest.write(req).getJson())
                    .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertAll(
            () -> assertEquals(response.getStatus(), HttpStatus.NO_CONTENT.value())
        );
    }
}
