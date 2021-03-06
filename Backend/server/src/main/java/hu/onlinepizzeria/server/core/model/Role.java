package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="role")
@Data
@Builder
public class Role {
    @Id
    @JsonView(Views.Public.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotEmpty
    @JsonView(Views.Public.class)
    String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
