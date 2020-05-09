package hu.onlinepizzeria.server.dao;

import hu.onlinepizzeria.server.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findUserByEmail(String email);
    @Query(value="SELECT * FROM user WHERE email = :email and password = :password", nativeQuery = true)
    Optional<User> findUserByEmailAndPassword(String email, String password);

    List<User> findAll();

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET password = :password where id = :id", nativeQuery = true)
    void updatePassword(int id, String password);
}
