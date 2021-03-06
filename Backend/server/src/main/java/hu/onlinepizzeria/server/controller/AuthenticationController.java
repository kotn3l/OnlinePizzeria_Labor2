package hu.onlinepizzeria.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;
import hu.onlinepizzeria.server.core.model.Role;
import hu.onlinepizzeria.server.core.model.User;
import hu.onlinepizzeria.server.dao.UserRepo;
import hu.onlinepizzeria.server.service.AuthenticationService;
import hu.onlinepizzeria.server.service.jwt.InvalidJwtAuthenticationException;
import hu.onlinepizzeria.server.service.jwt.JwtTokenProvider;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    // TODO Use this
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepo users;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @JsonView(Views.Public.class)
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Map<Object, Object>> loginUser(@RequestBody User user) throws IOException {

        Map<Object, Object> model = new HashMap<>();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            String token = jwtTokenProvider.createToken(user.getEmail(), this.users.findUserByEmail(user.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Username " + user.getEmail() + "not found"))
                    .getRoles());
            Optional<User> user2 = users.findUserByEmail(user.getEmail());
            model.put("session_string", token);
            model.put("role", user2.get().getRoles());
            return ok(model);

        } catch (AuthenticationException e) {
            model.put("error", "Invalid password or email.");
            return new ResponseEntity<>(model, HttpStatus.UNAUTHORIZED);
        }
    }

    @JsonView(Views.Public.class)
    @GetMapping("/logout")
    public ResponseEntity logout(@RequestParam(name="session_string", required = true) String session_string){
        return new ResponseEntity(HttpStatus.OK);
    }

    @JsonView(Views.Public.class)
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getUserRoles(@RequestParam(name="session_string", required = true) String session_string){
            Map<Object, Object> model = new HashMap<>();
            if (!jwtTokenProvider.validateToken(session_string)){
                model.put("error","Invalid session string.");
                return new ResponseEntity(model, HttpStatus.UNAUTHORIZED);
            }
            if (jwtTokenProvider.isAdmin(session_string)) {
                List<Map<Object, Object>> result = new ArrayList<>();
                for (Role r: authenticationService.getAllRoles()
                     ) {
                    model = new HashMap<>();
                    model.put("id", r.getId());
                    model.put("text", r.getName());
                    result.add(model);
                }
                return new ResponseEntity(result, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }

    @JsonView(Views.Public.class)
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestParam(name="session_string", required = true) String session_string,
                                       @RequestBody Map<String, Object> payload) {

        Map<Object, Object> model = new HashMap<>();
        try {
            if (!jwtTokenProvider.validateToken(session_string)){
                model.put("error","Invalid session string.");
                return new ResponseEntity(model, HttpStatus.UNAUTHORIZED);
            }
            if (jwtTokenProvider.isAdmin(session_string)) {
                User newUser = new User();
                String email = payload.get("email").toString();
                if(!EmailValidator.getInstance().isValid(email)
                        || users.findUserByEmail(email).isPresent()){
                    model.put("error", "Invalid email address");
                    return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
                }
                newUser.setEmail(email);
                newUser.setName(payload.get("name").toString());
                if (authenticationService.verifyRole(authenticationService.getAllRoles()
                        .get((int)payload.get("role_id")).getName())){

                    List<String> roles = new ArrayList<>();
                    roles.add(authenticationService.getAllRoles()
                            .get((int)payload.get("role_id")).getName());
                    newUser.setRoles(roles);
                }
                else return new ResponseEntity("Invalid role.", HttpStatus.BAD_REQUEST);
                newUser.setPassword(bCryptPasswordEncoder.encode("default"));
                //newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                users.save(newUser);
                return new ResponseEntity(newUser, HttpStatus.CREATED);
            }
            else {
                model.put("error","Invalid session string.");
                return new ResponseEntity(model, HttpStatus.UNAUTHORIZED);
            }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @JsonView(Views.Public.class)
    @PostMapping("/user")
    public ResponseEntity changePassword(@RequestParam(name="session_string", required = true) String session_string,
                                         @RequestParam(name = "password_old", required = true) String password_old,
                                         @RequestParam(name = "password_new", required = true) String password_new){

        String name = jwtTokenProvider.getUsername(session_string);
        String newPass = bCryptPasswordEncoder.encode(password_new);
        User u;
        if (users.findUserByEmail(name).isPresent()) {
            u = users.findUserByEmail(name).get();
            if (bCryptPasswordEncoder.matches(password_old, u.getPassword())){
                u.setPassword(newPass);
                authenticationService.updatePassword(u.getId(), u.getPassword());
                return new ResponseEntity(HttpStatus.OK);
            }
            else return new ResponseEntity("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
        else return new ResponseEntity("Incorrect username or password", HttpStatus.BAD_REQUEST);
    }

    @JsonView(Views.Public.class)
    @GetMapping("/user")
    public ResponseEntity getAllUsers(@RequestParam(name = "session_string", required = true) String session_string) {
        if (jwtTokenProvider.validateToken(session_string) && jwtTokenProvider.isAdmin(session_string)) {
            return new ResponseEntity(authenticationService.getAllUsers(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @JsonView(Views.Public.class)
    @DeleteMapping("/user")
    public ResponseEntity removeUser(@RequestParam(name = "session_string", required = true) String session_string,
                                     @RequestParam(name = "user_id", required = true) int user_id) {
        if (jwtTokenProvider.validateToken(session_string) && jwtTokenProvider.isAdmin(session_string)) {
            try {
                authenticationService.deleteUserById(user_id);
                return new ResponseEntity(HttpStatus.CREATED);
            }
            catch (Exception e) {
                return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @JsonView(Views.Public.class)
    @PostMapping("/auth")
    public ResponseEntity authSessionString(@RequestParam(name = "session_string") String session_string) {
        try {
            if (jwtTokenProvider.validateToken(session_string)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            else return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (InvalidJwtAuthenticationException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
