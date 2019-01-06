package pl.azurix.RoomUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import pl.azurix.room.RoomRepository;
import pl.azurix.user.UserRepository;

import java.util.List;
import java.util.Optional;

/*
 *  HOW TO USE:
 *
 * -add new user to room by id with POST
 * /room/<roomId>/add/user?userId=<userId>
 *     <roomId> Long
 *     <userId> Long
 *     return:
 *     HttpStatus.OK if user has been added
 *     HttpStatus.BAD_REQUEST if user hasn't been added
 *
 * -add new user to room by login with POST
 * /room/<roomId>/add?login=<login>
 *     <roomId> Long
 *     <login> String
 *     return:
 *     HttpStatus.OK if user has been added
 *     HttpStatus.BAD_REQUEST if user hasn't been added
 *
 * -get all users from room with GET
 * /room/<roomId>/users
 *     <roomId> Long
 *     return:
 *     Optional<List<RoomUser>> with all users
 *
 * -get all rooms where is user with GET
 * /user/<userId>/rooms
 *     <userId> Long
 *     return:
 *     Optional<List<RoomUser>> with all rooms
 */

@RestController
public class RoomUserController {
    @Autowired
    RoomUserRepository roomUserRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/room/{roomId}/add/user", method = RequestMethod.POST)
    HttpStatus addNewUser(@RequestParam Long userId, @PathVariable Long roomId) {
        return roomRepository.findById(roomId).map(room -> {
            return userRepository.findById(userId).map(user -> {
                if (roomUserRepository.findByRoomAndUser(room, user).size() > 0)
                    throw new ResourceNotFoundException("this user already exists in this room");
                else {
                    RoomUser rusr = new RoomUser(user, room);
                    roomUserRepository.save(rusr);
                    return HttpStatus.OK;
                }
            }).orElse(HttpStatus.BAD_REQUEST);
        }).orElse(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/room/{roomId}/add", method = RequestMethod.POST)
    HttpStatus addNewUser(@RequestParam String login, @PathVariable Long roomId) {
        return roomRepository.findById(roomId).map(room -> {
            return userRepository.findByLogin(login).map(user -> {
                if (roomUserRepository.findByRoomAndUser(room, user).size() > 0)
                    throw new ResourceNotFoundException("this user already exists in this room");
                else {
                    RoomUser rusr = new RoomUser(user, room);
                    roomUserRepository.save(rusr);
                    return HttpStatus.OK;
                }
            }).orElse(HttpStatus.BAD_REQUEST);
        }).orElse(HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/room/{roomId}/users", method = RequestMethod.GET)
    Optional<List<RoomUser>> selectUsers(@PathVariable Long roomId) {
        return roomRepository.findById(roomId).map(room -> roomUserRepository.findByRoom(room));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/user/{userId}/rooms", method = RequestMethod.GET)
    Optional<List<RoomUser>> getRooms(@PathVariable Long userId) {
        return userRepository.findById(userId).map(user -> roomUserRepository.findByUser(user));
    }


}

