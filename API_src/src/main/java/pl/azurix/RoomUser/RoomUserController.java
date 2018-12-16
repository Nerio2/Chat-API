package pl.azurix.RoomUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomUserController {

    @Autowired
    RoomUserRepository roomUserRepository;

    @RequestMapping("/add")
    @ResponseBody
    String addNewUser(@RequestParam String action, @RequestParam Long user_id, @RequestParam Long room) {
        if(action.equals("room_user_add")) {
            RoomUser usr = new RoomUser(user_id, room);
            roomUserRepository.save(usr);
            return "done";
        }else return "error, no action";
    }
}
