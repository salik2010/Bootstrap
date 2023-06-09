package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.Optional;


@Repository
public interface UserJpaRepository extends JpaRepository<User,Long> {
    @Query("Select u from User u left join fetch u.roles where u.email=:username")
    Optional<User> findByUsername(String username);
    User findUserByUsername(String name);

    Optional<User> findUserByEmail(String email);

}
