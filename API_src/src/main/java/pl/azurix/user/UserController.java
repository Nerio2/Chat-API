package pl.azurix.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

/*
 *  HOW TO USE:
 *
 * -create new user with POST
 * /register?email=<email>&login=<login>&password=<password>
 *     <email> String
 *     <login> String
 *     <password> String
 *     return: "200" if user has been created
 *     ResourceNotFoundException if user hasn't been created
 *
 * -login with POST
 * /login?login=<login>&password=<password>
 *     <login> String
 *     <password> String
 *     return: Object User
 *
 */

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestParam String login, @RequestParam String password) {
        return userRepository.findByLoginAndPassword(login, password).get();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String newUser(@RequestParam String email, @RequestParam String login, @RequestParam String password) {
        if(userRepository.findByLogin(login).isPresent())
            throw new ResourceNotFoundException("user with this login already exists");
        else {
            User user = new User(email, login, password);
            userRepository.save(user);
            return "200";
        }
    }
}
