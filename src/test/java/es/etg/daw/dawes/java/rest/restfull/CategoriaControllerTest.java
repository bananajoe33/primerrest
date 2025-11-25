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

import es.etg.daw.dawes.java.rest.restfull.productos.domain.model.CategoriaId;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.db.repository.mock.CategoriaFactory;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto.CategoriaRequest;
import es.etg.daw.dawes.java.rest.restfull.productos.infraestructure.web.dto.CategoriaResponse;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoriaControllerTest {

    public static String ENDPOINT = "/categorias";

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private JacksonTester<CategoriaRequest> jsonCategoriaRequest;

    @Autowired
    private JacksonTester<CategoriaResponse> jsonCategoriaResponse;

    @BeforeEach
    public void setUp() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    @Order(1)
    public void getAll() throws Exception {
        int numCategorias = CategoriaFactory.getDemoData().values().size();

        MockHttpServletResponse response = mockMvc.perform(
                get(ENDPOINT).accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        List<CategoriaResponse> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, CategoriaResponse.class));

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertTrue(res.size() == numCategorias)
        );
    }

    @Test
    @Order(10)
    public void save() throws Exception {
        String nombreValido = "CategoriaValida";
        CategoriaRequest req = new CategoriaRequest(nombreValido);

        MockHttpServletResponse response = mockMvc.perform(
                post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCategoriaRequest.write(req).getJson())
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        CategoriaResponse res = mapper.readValue(response.getContentAsString(), CategoriaResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), response.getStatus()),
                () -> assertEquals(res.nombre(), nombreValido),
                () -> assertTrue(res.id() > 0)
        );
    }

    @Test
    @Order(11)
    public void saveEmptyNombreShouldFail() throws Exception {
        CategoriaRequest req = new CategoriaRequest(""); // nombre vacío

        MockHttpServletResponse response = mockMvc.perform(
                post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCategoriaRequest.write(req).getJson())
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Solo comprobamos que la respuesta sea 400 BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    
    @Test
    @Order(20)
    public void update() throws Exception {
        String nombreEditado = "NombreEditado";
        CategoriaId id = new CategoriaId(1);
        CategoriaRequest req = new CategoriaRequest(nombreEditado);

        MockHttpServletResponse response = mockMvc.perform(
                put(ENDPOINT + "/" + id.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCategoriaRequest.write(req).getJson())
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        CategoriaResponse res = mapper.readValue(response.getContentAsString(), CategoriaResponse.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.nombre(), nombreEditado),
                () -> assertEquals(res.id(), id.getValue())
        );
    }

    @Test
    @Order(30)
    public void deletes() throws Exception {
        CategoriaId id = new CategoriaId(1);

        MockHttpServletResponse response = mockMvc.perform(
                delete(ENDPOINT + "/" + id.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertAll(
                () -> assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus())
        );
    }
}
