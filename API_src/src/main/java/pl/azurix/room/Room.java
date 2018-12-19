package pl.azurix.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import pl.azurix.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "rooms")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "creator_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User creator;

    @NotNull
    @Column(unique = true)
    private String name;

    Room() {
    }

    Room(User creator, String name) {
        this.creator = creator;
        this.name = name;
    }

    public User getCreator() { return creator; }

    public void setCreator(User creator) { this.creator = creator; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
