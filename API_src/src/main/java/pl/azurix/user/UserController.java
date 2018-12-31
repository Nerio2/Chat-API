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
 * -change password with PUT
 * /user/<userId>/password?password=<password>&newPassword=<newPassword>
 *     <userId> Long
 *     <password> String
 *     <newPassword> String
 *     return: "200" if password has been edited
 *     ResourceNotFoundException if password hasn't been edited
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

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "user/{userId}/password", method = RequestMethod.PUT)
    public String changePassword(@PathVariable Long userId, @RequestParam String password, @RequestParam String newPassword){
        return userRepository.findById(userId).map(user -> {
            if(password.equals(user.getPassword())) {
                user.setPassword(newPassword);
                userRepository.save(user);
                return "200";
            }
            else throw new ResourceNotFoundException("wrong password");
        }).orElseThrow(()->new ResourceNotFoundException("user id "+userId+" not found"));

    }

}