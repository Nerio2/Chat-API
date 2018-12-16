package pl.azurix.room;

import javax.persistence.*;
import java.util.List;

import pl.azurix.message.*;

@Table(name = "rooms")
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_id")
    private Long creatorId;
    private String name;

    Room() {
    }

    Room(Long creatorId, String name) {
        this.creatorId = creatorId;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getName() {
        return name;
    }
}
