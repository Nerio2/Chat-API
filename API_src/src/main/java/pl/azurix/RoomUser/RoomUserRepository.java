package pl.azurix.RoomUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.repository.CrudRepository;
import pl.azurix.room.Room;
import pl.azurix.user.User;

import java.util.List;

public interface RoomUserRepository extends CrudRepository<RoomUser, Long> {
    List<RoomUser> findByRoom(Room room);

    List<RoomUser> findByUser(User user);

    List<RoomUser> findByRoomAndUser(Room room, User user);
}
