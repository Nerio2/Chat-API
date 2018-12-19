package pl.azurix.message;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByRoomIdOrderByIdDesc(Long RoomId, Pageable count);
}
