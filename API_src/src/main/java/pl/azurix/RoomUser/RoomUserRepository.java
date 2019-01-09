package pl.azurix.RoomUser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.azurix.room.Room;
import pl.azurix.user.User;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends CrudRepository<RoomUser, Long> {

    @Query(value = "SELECT t.user_id FROM room_users t WHERE t.room_id = :room", nativeQuery = true)
    List<BigInteger> getUsersFromRoom(@Param("room") Room room);

    List<RoomUser> findByUser(User user);

    List<RoomUser> findByRoomAndUser(Room room, User user);
}
