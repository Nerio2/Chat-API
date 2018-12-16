package pl.azurix.message;

import javax.persistence.*;

@Table(name = "messages")
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "room_id")
    Long roomId;

    @Column(name = "sender_id")
    Long senderId;

    String message;

    Message() {
    }

    Message(Long roomId, Long senderId, String message) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }
}
