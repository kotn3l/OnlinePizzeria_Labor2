package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name="user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
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
    @Builder.Default
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
}