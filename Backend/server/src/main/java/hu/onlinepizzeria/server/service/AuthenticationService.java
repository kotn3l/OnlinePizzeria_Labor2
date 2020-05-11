package hu.onlinepizzeria.server.service;

import hu.onlinepizzeria.server.core.model.Role;
import hu.onlinepizzeria.server.core.model.User;
import hu.onlinepizzeria.server.dao.RoleRepo;
import hu.onlinepizzeria.server.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationService implements UserDetailsManager, UserDetailsService {
    @Autowired
    UserRepo users;

    @Autowired
    RoleRepo roles;

    public AuthenticationService(UserRepo users) {
        this.users = users;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        
    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    public void deleteUserById(int id) {
        users.deleteById(id);
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

    public UserDetails loadUserByLogin(String email, String password) throws UsernameNotFoundException {
        if (userExists(email)) {
            return users.findUserByEmailAndPassword(email, password)
                    .orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
        } else {
            throw new UsernameNotFoundException("Not found: " + email);
        }

    }

    public List<User> getAllUsers() {
        return users.findAll();
    }

    public List<Role> getAllRoles() {
        List<String> roleList = roles.getAllRoles();
        System.out.println(roleList);
        List<Role> allRoles = new ArrayList<>();
        int i = 1;
        for (String role1 : roleList) {
            Role r = Role.builder().id(i).name(role1).build();
            allRoles.add(r);
            i++;
        }
        return allRoles;
    }

    public String getRoleById(int role_id) {
        return roles.getRoleById(role_id);
    }

    public void updatePassword(int id, String password) {
        users.updatePassword(id, password);
        System.out.println(id + " " + password);
    }

    public boolean verifyRole(String role) {
        if (role == null)
            return false;
        List<Role> roles = getAllRoles();
        for (Role r : roles
        ) {
            if (r.getName().equals(role))
                return true;
        }
        return false;
    }
}
