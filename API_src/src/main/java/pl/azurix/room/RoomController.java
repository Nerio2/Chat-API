package pl.azurix.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.azurix.RoomUser.RoomUser;
import pl.azurix.RoomUser.RoomUserRepository;
import pl.azurix.user.UserRepository;

/*
 *  HOW TO USE:
 *
 * -create new room with POST
 * /room/new?creatorId=<creatorId>&name=<name>
 *     <creatorId> Long
 *     <name> String
 *     return: "200" room has been created
 *     ResourceNotFoundException if room hasn't been created
 *
 * -get all rooms with GET
 * /rooms
 *     return: Iterable<Room> with all rooms
 */

@RestController
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomUserRepository roomUserRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/room/new", method = RequestMethod.POST)
    public String newRoom(@RequestParam Long creatorId, @RequestParam String name) {
        return userRepository.findById(creatorId).map(user -> {
            if(roomRepository.findByCreatorAndName(user,name).isPresent())
                throw new ResourceNotFoundException("this user already got a room with name: "+name);
            else {
                Room room = new Room(user, name);
                roomRepository.save(room);
                RoomUser roomUsr = new RoomUser(user, room);
                roomUserRepository.save(roomUsr);
                return "200";
            }
        }).orElseThrow(() -> new ResourceNotFoundException("user id " + creatorId + " not found"));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }

}
/*
    //update example
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
        return postRepository.findById(postId).map(post -> {
            post.setTitle(postRequest.getTitle());
            post.setDescription(postRequest.getDescription());
            post.setContent(postRequest.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    //delete example
     public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
 */