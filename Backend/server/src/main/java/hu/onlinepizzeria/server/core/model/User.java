package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    public User(String email, String password, String name, List<String> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    @Id
    @JsonView(Views.Public.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(name = "email")
    @NotEmpty
    @JsonView(Views.Public.class)
    private String email;

    @NotEmpty
    @JsonView(Views.Public.class)
    private String password;

    @Column(name = "name")
    @JsonView(Views.Public.class)
    private String name;

    public String getName() {
        return name;
    }

    @JsonView(Views.Public.class)
    @ElementCollection(fetch = FetchType.EAGER)
    //@JoinColumn(name = "role", nullable = false)
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static class UserBuilder {
        private int id;
        private String name;
        private String email;
        private String password;
        private List<String> roles;

        public UserBuilder(String email) {
            this.email = email;
        }

        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setRoles(String... role) {
            this.roles.addAll(Arrays.asList(role));
            return this;
        }

        public UserBuilder setRoles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public User build() { return new User(this.id, this.email, this.password, this.name, this.roles); }
    }
}