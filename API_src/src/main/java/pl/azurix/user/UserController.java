package pl.azurix.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.azurix.WebSecurityConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/*
 *  HOW TO USE:
 *
 * -create new user with POST
 * /auth/register?email=<email>&login=<login>&password=<password>
 *     <email> String
 *     <login> String
 *     <password> String
 *     return: "200" if user has been created
 *     ResourceNotFoundException if user hasn't been created
 *
 * -login with POST
 * /auth/login?login=<login>&password=<password>
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

    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Autowired
    HttpServletRequest httpServletRequest;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/root/users", method = RequestMethod.GET)
    public Iterable<User> getUsers(){return userRepository.findAll();}

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public User login(@RequestParam String login, @RequestParam String password) {
        Optional<User> usr = userRepository.findByLoginAndPassword(login, password);
        if (usr != null) {
            User user = usr.get();
            switch ( user.getAuthLvl() ) {
                case 0:
                    webSecurityConfig.authWithHttpServletRequest(httpServletRequest, WebSecurityConfig.ROOT_LOGIN, WebSecurityConfig.ROOT_PASSWORD);
                    break;
                case 1:
                    webSecurityConfig.authWithHttpServletRequest(httpServletRequest, WebSecurityConfig.USER_LOGIN, WebSecurityConfig.USER_PASSWORD);
                    break;
            }

            return user;
        } else return null;
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/auth/register", method = RequestMethod.GET)
    public String newUser(@RequestParam String email, @RequestParam String login, @RequestParam String password) {
        if (userRepository.findByLogin(login).isPresent())
            throw new ResourceNotFoundException("user with this login already exists");
        else {
            User user = new User(email, login, password, 1);
            userRepository.save(user);
            return "200";
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "user/{userId}/password", method = RequestMethod.PUT)
    public String changePassword(@PathVariable Long userId, @RequestParam String password, @RequestParam String newPassword) {
        return userRepository.findById(userId).map(user -> {
            if (password.equals(user.getPassword())) {
                user.setPassword(newPassword);
                userRepository.save(user);
                return "200";
            } else throw new ResourceNotFoundException("wrong password");
        }).orElseThrow(() -> new ResourceNotFoundException("user id " + userId + " not found"));

    }

}