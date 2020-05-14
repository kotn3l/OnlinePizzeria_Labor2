package hu.onlinepizzeria.server.core.model;

import com.fasterxml.jackson.annotation.JsonView;
import hu.onlinepizzeria.server.core.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "user_roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @JsonView(Views.Public.class)
    Integer user_id;

    @NotNull
    @JsonView(Views.Public.class)
    String roles;
}
