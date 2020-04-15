package hu.onlinepizzeria.server.dao;

import hu.onlinepizzeria.server.core.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepo extends CrudRepository<UserRole, Integer> {
    @Query(value = "SELECT * from user_roles where user_id=:user_id", nativeQuery = true)
    List<UserRole> getUserRoleByUser_id(int user_id);

    @Query(value = "SELECT name from role", nativeQuery = true)
    List<String> getAllRoles();

    @Query(value = "SELECT name from role where id=:role_id", nativeQuery = true)
    String getRoleById(int role_id);
}
