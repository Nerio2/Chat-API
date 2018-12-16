package pl.azurix.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping("/room/message")
    @ResponseBody
    public String newMessage(@RequestParam String action, @RequestParam Long room, @RequestParam Long sender_id, @RequestParam String message) {
        if(action.equals("new_message")) {
            Message msg = new Message(room, sender_id, message);
            messageRepository.save(msg);
            return message;
        } else return "error, no action";
    }

    @RequestMapping("/room")
    @ResponseBody
    public List<Object> getMessages(@RequestParam String action, @RequestParam Long room, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        if(action.equals("get_messages")) {
            Page<Object> val = messageRepository.getMessages(room, PageRequest.of(0, limit));
            return val.getContent();
        } else return new ArrayList<Object>(Arrays.asList("error, no action"));
    }



}