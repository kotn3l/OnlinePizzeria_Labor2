package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.core.model.User;
import hu.onlinepizzeria.server.dao.UserRepo;
import hu.onlinepizzeria.server.service.AuthenticationService;
import hu.onlinepizzeria.server.service.jwt.JwtTokenProvider;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path="/api")
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
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Map<Object, Object>> loginUser(@RequestBody User user) throws IOException {
        //UserRole role = new UserRole(3L, "ROLE_USER");
        //role.setRoles("ROLE_" + Role.values()[4]);

        try {
            System.out.println(user.getEmail() + user.getPassword());
            //System.out.println(bCryptPasswordEncoder.encode(user.getPassword()));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            String token = jwtTokenProvider.createToken(user.getEmail(), this.users.findUserByEmail(user.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Username " + user.getEmail() + "not found"))
                    .getRoles());
            Optional<User> user2 = users.findUserByEmail(user.getEmail());
            System.out.println("roles" + user2.get().getRoles());
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
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestParam(name="session_string", required = true) String session_string,
                                       @RequestBody User user) {
        System.out.println(jwtTokenProvider.getAuthentication(session_string).getAuthorities());

        if (jwtTokenProvider.getAuthentication(session_string).getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            User newUser = new User();
            if(!EmailValidator.getInstance().isValid(user.getEmail()) || users.findUserByEmail(user.getEmail()).isPresent()){
                return new ResponseEntity("Invalid email address", HttpStatus.BAD_REQUEST);
            }
            newUser.setEmail(user.getEmail());
            newUser.setName("Jacob Gypsum");
            newUser.setRoles(user.getRoles());
            newUser.setPassword(bCryptPasswordEncoder.encode("default"));
            users.save(newUser);
            return new ResponseEntity(newUser, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
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
    @GetMapping("/user")
    public ResponseEntity getAllUsers(@RequestParam(name = "session_string", required = true) String session_string) {
        if (jwtTokenProvider.getAuthentication(session_string).getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return new ResponseEntity(authenticationService.getAllUsers(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
