package hu.onlinepizzeria.server.core.model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @NotEmpty
    String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
