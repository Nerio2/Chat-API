package pl.azurix.message;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;


public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query(value = "SELECT t.senderId, t.message FROM Message t WHERE t.roomId=:roomId ORDER BY t.id DESC")
    Page<Object> getMessages(@Param("roomId") Long roomId, Pageable pageable);
}
