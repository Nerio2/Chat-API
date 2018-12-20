package pl.azurix.room;

import org.springframework.data.repository.CrudRepository;
import pl.azurix.user.User;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Long> {
    List<Room> findByCreatorAndName(User creator, String name);
}
