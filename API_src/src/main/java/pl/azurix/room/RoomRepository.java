package pl.azurix.room;

import org.springframework.data.repository.CrudRepository;
import pl.azurix.user.User;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Long> {
    Optional<Room> findByCreatorAndName(User creator, String name);
}
