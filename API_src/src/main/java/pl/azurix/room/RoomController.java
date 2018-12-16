package pl.azurix.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping("/room/new")
    @ResponseBody
    public Object newRoom(@RequestParam String action, @RequestParam Long creator_id, @RequestParam String name){
        if(action.equals("new_room")) {
            Room room = new Room(creator_id, name);
            roomRepository.save(room);
            return room;
        } else return "error, no action";
    }
}
