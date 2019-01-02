package pl.azurix.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long> {
    @PreAuthorize("permitAll()")
    Optional<User> findByLoginAndPassword(String login, String password);
    Optional<User> findByLogin(String login);
    Optional<User> findByIdAndPassword(Long id, String password);
}
