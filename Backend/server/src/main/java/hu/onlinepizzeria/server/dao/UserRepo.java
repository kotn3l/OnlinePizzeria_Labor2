package hu.onlinepizzeria.server.dao;

import hu.onlinepizzeria.server.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findUserByEmail(String email);
    @Query(value="SELECT * FROM user_role WHERE email = :email and password = :password", nativeQuery = true)
    Optional<User> findUserByEmailAndPassword(String email, String password);
    // User save(User user);
}
