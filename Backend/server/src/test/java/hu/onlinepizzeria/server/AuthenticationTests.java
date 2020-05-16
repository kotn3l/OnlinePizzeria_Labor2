package hu.onlinepizzeria.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.onlinepizzeria.server.core.model.User;
import hu.onlinepizzeria.server.dao.RoleRepo;
import hu.onlinepizzeria.server.dao.UserRepo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.liquibase.enabled=false",
        "spring.flyway.enabled=false"
})
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class AuthenticationTests {

    private String session_string = "";
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @BeforeClass
    public static void setUpClass() {

    }

    @BeforeEach
    void setUp() {
        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_ADMIN");
        User u = new User.UserBuilder("gipsum@citromail.hu")
                .setId(1)
                .setName("Józsi")
                .setPassword(bCryptPasswordEncoder.encode("jelszó"))
                .setRoles(roles)
                .build();
        userRepo.save(u);

        roles = new ArrayList<String>();
        roles.add("ROLE_DELIVERY");
        User u2 = new User.UserBuilder("fszallito@citromail.hu")
                .setId(2)
                .setName("Józsi")
                .setPassword(bCryptPasswordEncoder.encode("jelszó2"))
                .setRoles(roles)
                .build();
        userRepo.save(u2);
    }
    @BeforeEach
    void setSession_string() throws JSONException, Exception{
        String jsonString = new JSONObject()
                .put("email", "gipsum@citromail.hu")
                .put("password", "jelszó")
                .toString();
        String result = mvc.perform(post("/api/login")
                .contentType("application/json")
                .content(jsonString)).andReturn().getResponse().getContentAsString();
        System.out.println(result);
        JSONObject resultJSON = new JSONObject(result);
        session_string = resultJSON.getString("session_string");
    }
    @Test
    public void getLoginResult() throws JSONException, Exception {
        String jsonString = new JSONObject()
                .put("email", "gipsum@citromail.hu")
                .put("password", "jelszó")
                .toString();
        String result = mvc.perform(post("/api/login")
        .contentType("application/json")
                .content(jsonString)).andReturn().getResponse().getContentAsString();
        JSONObject resultJSON = new JSONObject(result);
        JSONArray resultRole = resultJSON.getJSONArray("role");
        String role = resultRole.get(0).toString();

        assertEquals("ROLE_ADMIN", role);

    }
    @Test
    public void registerUserTest() throws JSONException, Exception {
        roleRepo.addNewRole(0, "ROLE_ADMIN");
        String jsonString = new JSONObject()
                .put("email", "ki@be.com")
                .put("name", "jelszó")
                .put("role_id", 0).toString();

        mvc.perform(post("/api/register")
                .contentType("application/json")
                .param("session_string", session_string)
                .content(jsonString))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    public void registerInvalidEmailTest() throws JSONException, Exception {
        String jsonString = new JSONObject()
                .put("email", "kis joli")
                .put("name", "jelszó")
                .put("role_id", 0)
                .toString();

        mvc.perform(post("/api/register")
                .contentType("application/json")
                .param("session_string", session_string)
                .content(jsonString))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void getAllUsersTest() throws Exception {
                mvc.perform(get("/api/user")
                .contentType("application/json")
                .param("session_string", session_string))
                .andExpect(status().isOk());
    }
    @Test
    public void removeUserTest() throws Exception {
        mvc.perform(delete("/api/user")
                .contentType("application/json")
                .param("session_string", session_string)
                .param("user_id", String.valueOf(2)))
                .andExpect(status().isCreated());
    }
    @Test
    public void changePasswordTest() throws Exception {
        User u = userRepo.findById(1).get();
        String newPass = bCryptPasswordEncoder.encode("jelszó2");
        mvc.perform(post("/api/user")
                .contentType("application/json")
                .param("session_string", session_string)
                .param("password_old", "jelszó")
                .param("password_new", newPass))
                .andExpect(status().isOk());
        assertTrue(bCryptPasswordEncoder.matches(newPass, userRepo.findById(1).get().getPassword()));
    }

}
