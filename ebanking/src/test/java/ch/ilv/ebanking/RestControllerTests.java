package ch.ilv.ebanking;

import ch.ilv.ebanking.address.Address;
import ch.ilv.ebanking.address.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestControllerTests {
    @Autowired
    private MockMvc api;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeAll
    void setup() {
        this.addressRepository.save(new Address("Wall Street", 94, "New York", "USA"));
        this.addressRepository.save(new Address("Broadway", 562, "New York", "USA"));
    }

    @Test
    @Order(1)
    void testGetAddresses() throws Exception {
        String accessToken = obtainAccessToken();

        api.perform(get("/api/Address")
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Wall Street")));
    }

    @Test
    @Order(2)
    void testSaveVehicle() throws Exception {
        Address address = new Address();

        address.setStreet("William Street");
        address.setStreetNumber(123);
        address.setCity("New York");
        address.setCountry("USA");

        String accessToken = obtainAccessToken();
        String body = new ObjectMapper().writeValueAsString(address);

        api.perform(post("/api/Address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("William Street")));
    }

    @Test
    @Order(3)
    void testUpdateAddress() throws Exception {

        Address address = new Address();

        /*address.setStreet("William Street");
        address.setStreetNumber(123);
        address.setCity("New York");
        address.setCountry("USA");*/

        String accessToken = obtainAccessToken();
        String body = new ObjectMapper().writeValueAsString(address);

        api.perform(put("/api/Address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("William Street")));
    }

    @Test
    @Order(4)
    void testDeleteAddress() throws Exception {

        Address address = new Address();

        /*address.setStreet("William Street");
        address.setStreetNumber(123);
        address.setCity("New York");
        address.setCountry("USA");*/

        String accessToken = obtainAccessToken();
        String body = new ObjectMapper().writeValueAsString(address);

        api.perform(delete("/api/Address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("William Street")));
    }

    private String obtainAccessToken() {

        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=demoapp&" +
                "grant_type=password&" +
                "scope=openid profile roles offline_access&" +
                "username=admin&" +
                "password=admin";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> resp = rest.postForEntity("http://localhost:8080/realms/ILV/protocol/openid-connect/token", entity, String.class);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resp.getBody()).get("access_token").toString();
    }
}