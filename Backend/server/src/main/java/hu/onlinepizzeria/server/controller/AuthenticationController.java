package hu.onlinepizzeria.server.controller;

import hu.onlinepizzeria.server.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public @ResponseBody String loginUser(@RequestParam String email, @RequestParam String password) {
        try {
            System.out.println(email + password);
            //UserDetails user = authenticationService.loadUserByLogin(email, password);
            //System.out.println(user.getUsername()+ user.getPassword() + user.getAuthorities());
            return String.valueOf(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password)));
            //return "user.getPassword() + user.getAuthorities();"+ u.toString();
            //UserDetails user = authenticationService.loadUserByLogin(email, password);
            //return user.getPassword() + user.getAuthorities();
        } catch (AuthenticationException e) {
            //throw new BadCredentialsException("Invalid username/password");
            throw e;
        }

        //User user = authenticationService.loadUserByLogin(email, password);


    }

}
