package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService implements UserDetailsManager, UserDetailsService {
    @Autowired
    UserRepo users;

    public AuthenticationService(UserRepo users) {
        this.users = users;
    }

    @Override
    public void createUser(UserDetails userDetails) {
       // User user =  new User(userDetails.getUsername())
        // users.save();
    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String s) {
        return users.findUserByEmail(s).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return users.findUserByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + s));

    }
    // TODO Add password exception @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public UserDetails loadUserByLogin(String email, String password) throws UsernameNotFoundException {
        if (userExists(email)) {
            return users.findUserByEmailAndPassword(email, password)
                    .orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
        }
        else {
            throw new UsernameNotFoundException("Not found: " + email);
        }

    }
}