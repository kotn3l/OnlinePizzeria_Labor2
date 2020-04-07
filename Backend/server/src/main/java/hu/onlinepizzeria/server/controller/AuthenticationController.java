package hu.onlinepizzeria.server.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.onlinepizzeria.server.dao.UserRepo;
import hu.onlinepizzeria.server.service.AuthenticationService;
import hu.onlinepizzeria.server.service.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path="/api")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepo users;
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Map<Object, Object>> loginUser(@RequestBody String request) throws IOException {
        JsonNode rootNode = new ObjectMapper().readTree(new StringReader(request));
        JsonNode eField = rootNode.get("email");
        JsonNode pField = rootNode.get("password");
        String email = eField.asText();
        String password = pField.asText();
        try {
            System.out.println(email + password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String token = jwtTokenProvider.createToken(email, this.users.findUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Username " + email + "not found"))
                    .getRoles());

            Map<Object, Object> model = new HashMap<>();
            //model.put("email", email);
            model.put("session_string", token);
            return ok(model);
            //UserDetails user = authenticationService.loadUserByLogin(email, password);
            //System.out.println(user.getUsername()+ user.getPassword() + user.getAuthorities());
            //return String.valueOf(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)));
            //return "user.getPassword() + user.getAuthorities();"+ u.toString();
            //UserDetails user = authenticationService.loadUserByLogin(email, password);
            //return user.getPassword() + user.getAuthorities();
        } catch (AuthenticationException e) {
            //throw new BadCredentialsException("Invalid username/password");
            throw new BadCredentialsException("Invalid username/password supplied");
        }

        //User user = authenticationService.loadUserByLogin(email, password);


    }
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestParam String session_string, @RequestBody String request) {

        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        try {
            model.put("username", userDetails.getUsername());
            model.put("roles", userDetails.getAuthorities()
                    .stream()
                    .map(a -> ((GrantedAuthority) a).getAuthority())
                    .collect(toList())
            );

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return ok(model);
    }

}
