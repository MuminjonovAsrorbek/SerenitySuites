package uz.dev.serenitysuites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.serenitysuites.entity.User;
import uz.dev.serenitysuites.exceptions.EntityNotFoundException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    default User findByEmailOrThrow(String email) {

        return this.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email, HttpStatus.NOT_FOUND));

    }

    default User findByIdOrThrow(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id, HttpStatus.NOT_FOUND));

    }

    boolean existsByEmail(String mail);

}