package pl.azurix.room;

import org.springframework.data.repository.CrudRepository;
import pl.azurix.RoomUser.RoomUser;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
