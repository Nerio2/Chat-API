package pl.azurix.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.azurix.room.RoomRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import pl.azurix.user.UserRepository;

import java.util.*;

/*
 *  HOW TO USE:
 *
 * -create new message with POST
 * /room/<roomId>/message?senderId=<senderId>&message=<message>
 *     <roomId> Long
 *     <senderId> Integer
 *     <message> String
 *     return: "200" if message has been created
 *     ResourceNotFoundException if message hasn't been created
 *
 * -delete message with DELETE
 * /room/<roomId>/message?messageId=<messageId>
 *     <roomId> Long
 *     <messageId> Long
 *     return "200" if message has been deleted
 *     ResourceNotFoundException if message hasn't been deleted
 *
 * -get messages from room with GET
 * /room/<roomId>?requestCount=<requestCount>
 *     <roomId> Long
 *     <requestCount> Integer (default value= 0)    for val=0- first 10 messages, val=1- messages from 11 to 20, val=3- 21-30
 *     return: List<Message>
 *
 * -edit message with PUT
 * /room/<roomId>?message=<Message>
 *     <roomId> Long
 *     <Message> Message
 *     return: "200" if message has been edited
 *     ResourceNotFoundException if message hasn't been edited
 */

@RestController
public class MessageController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/room/{roomId}/message", method = RequestMethod.POST)
    public String newMessage(@PathVariable(value = "roomId") Long roomId, @RequestParam Long senderId, @RequestParam String message) {
        return roomRepository.findById(roomId).map(room -> {
                return userRepository.findById(senderId).map(user -> {
                    Message msg = new Message(room, user, message);
                    messageRepository.save(msg);
                    return "200";
            }).orElseThrow(() -> new ResourceNotFoundException("sender id " + senderId + " not found"));
        }).orElseThrow(() -> new ResourceNotFoundException("room id " + roomId + " not found"));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.GET)
    public List<Message> getMessages(@PathVariable("roomId") Long roomId, @RequestParam(value = "count", defaultValue = "20") Integer count) {
        Pageable limit = PageRequest.of(0, count);
        return messageRepository.findByRoomIdOrderByIdDesc(roomId, limit);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.DELETE)
    public String deleteMessage(@PathVariable("roomId") Long roomId, @RequestParam Long messageId){
        return roomRepository.findById(roomId).map(room -> {
                return messageRepository.findById(messageId).map(message -> {
                    messageRepository.delete(message);
                    return "200";
            }).orElseThrow(() -> new ResourceNotFoundException("message id " + messageId + " not found"));
        }).orElseThrow(() -> new ResourceNotFoundException("room id " + roomId + " not found"));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.PUT)
    public String editMessage(@PathVariable("roomId") Long roomId, @RequestBody Message message){
        Long messageId=message.getId();
        return roomRepository.findById(roomId).map(room -> {
                return messageRepository.findById(messageId).map(messageIn -> {
                    messageIn.setMessage(message.getMessage());
                    messageRepository.save(messageIn);
                    return "200";
            }).orElseThrow(() -> new ResourceNotFoundException("message id " + messageId + " not found"));
        }).orElseThrow(() -> new ResourceNotFoundException("room id " + roomId + " not found"));
    }
}
/*
 * IN THE FUTURE:
 * -edit message?
 * -delete message?
 *
 */