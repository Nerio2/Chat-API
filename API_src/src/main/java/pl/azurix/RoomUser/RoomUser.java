package pl.azurix.RoomUser;

import javax.persistence.*;


@Table(name = "room_users")
@Entity
public class RoomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "room_id")
    Long roomId;

    public RoomUser(Long userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRoomId() {
        return roomId;
    }
}
