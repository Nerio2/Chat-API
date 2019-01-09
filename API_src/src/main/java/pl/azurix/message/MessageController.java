package pl.azurix.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.azurix.room.RoomRepository;
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
 *     return:
 *     HttpStatus.CREATED if message has been created
 *     HttpStatus.BAD_REQUEST if message hasn't been created
 *
 * -delete message with DELETE
 * /room/<roomId>/message?messageId=<messageId>
 *     <roomId> Long
 *     <messageId> Long
 *     return:
 *     HttpStatus.OK if message has been deleted
 *     HttpStatus.BAD_REQUEST if message hasn't been deleted
 *
 * -get messages from room with GET
 * /room/<roomId>?requestCount=<requestCount>
 *     <roomId> Long
 *     <requestCount> Integer (default value= 20)
 *     return:
 *     List<Message>
 *
 * -edit message with PUT
 * /room/<roomId>?message=<Message>
 *     <roomId> Long
 *     <Message> Message
 *     return:
  *    HttpStatus.OK if message has been edited
 *     HttpStatus.BAD_REQUEST if message hasn't been edited
 */

@RestController
public class MessageController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/room/{roomId}/message", method = RequestMethod.POST)
    public HttpStatus newMessage(@PathVariable(value = "roomId") Long roomId, @RequestParam Long senderId, @RequestParam String message) {
        return roomRepository.findById(roomId).map(room -> {
                return userRepository.findById(senderId).map(user -> {
                    Message msg = new Message(room, user, message);
                    messageRepository.save(msg);
                    return HttpStatus.CREATED;
            }).orElse(HttpStatus.BAD_REQUEST);
        }).orElse(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.GET)
    public List<Message> getMessages(@PathVariable("roomId") Long roomId, @RequestParam(value = "count", defaultValue = "20") Integer count) {
        Pageable limit = PageRequest.of(0, count);
        return messageRepository.findByRoomIdOrderByIdDesc(roomId, limit);
    }

    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.DELETE)
    public HttpStatus deleteMessage(@PathVariable("roomId") Long roomId, @RequestParam Long messageId){
        return roomRepository.findById(roomId).map(room -> {
                return messageRepository.findById(messageId).map(message -> {
                    messageRepository.delete(message);
                    return HttpStatus.OK;
            }).orElse(HttpStatus.BAD_REQUEST);
        }).orElse(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/room/{roomId}", method = RequestMethod.PUT)
    public HttpStatus editMessage(@PathVariable("roomId") Long roomId, @RequestBody Message message){
        Long messageId=message.getId();
        return roomRepository.findById(roomId).map(room -> {
                return messageRepository.findById(messageId).map(messageIn -> {
                    messageIn.setMessage(message.getMessage());
                    messageRepository.save(messageIn);
                    return HttpStatus.OK;
            }).orElse(HttpStatus.BAD_REQUEST);
        }).orElse(HttpStatus.BAD_REQUEST);
    }
}