package pl.azurix.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.azurix.WebSecurityConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/*
 *  HOW TO USE:
 *
 * -create new user with GET
 * /auth/register?email=<email>&login=<login>&password=<password>
 *     <email> String
 *     <login> String
 *     <password> String
 *     return:
 *    HttpStatus.CREATED if user has been created
 *     HttpStatus.BAD_REQUEST if user hasn't been created
 *
 * -login with GET
 * /auth/login?login=<login>&password=<password>
 *     <login> String
 *     <password> String
 *     return:
 *     Object User
 *
 * -change password with PUT
 * /user/<userId>/password?password=<password>&newPassword=<newPassword>
 *     <userId> Long
 *     <password> String
 *     <newPassword> String
 *     return:
 *     HttpStatus.OK if password has been edited
 *     HttpStatus.BAD_REQUEST if password hasn't been edited
 *
 * -Get all users with GET
 * /root/users
 *     return:
 *     Iterable<User>
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
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public User login(@RequestParam String login, @RequestParam String password) {
        Optional<User> usr = userRepository.findByLoginAndPassword(login, password);
        if (usr.isPresent()) {
            User user = usr.get();
            switch ( user.getAuthLvl() ) {
                case 0: //ROOT
                    webSecurityConfig.authWithHttpServletRequest(httpServletRequest, WebSecurityConfig.ROOT_LOGIN, WebSecurityConfig.ROOT_PASSWORD);
                    break;
                case 1: //USER
                    webSecurityConfig.authWithHttpServletRequest(httpServletRequest, WebSecurityConfig.USER_LOGIN, WebSecurityConfig.USER_PASSWORD);
                    break;
            }
            return user;
        } else return null;
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/auth/register", method = RequestMethod.GET)
    public HttpStatus newUser(@RequestParam String email, @RequestParam String login, @RequestParam String password) {
        if (userRepository.findByLogin(login).isPresent())
            return HttpStatus.BAD_REQUEST;
        else {
            User user = new User(email, login, password, 1);
            userRepository.save(user);
            return HttpStatus.CREATED;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "user/{userId}/password", method = RequestMethod.PUT)
    public HttpStatus changePassword(@PathVariable Long userId, @RequestParam String password, @RequestParam String newPassword) {
        return userRepository.findById(userId).map(user -> {
            if (password.equals(user.getPassword())) {
                user.setPassword(newPassword);
                userRepository.save(user);
                return HttpStatus.OK;
            } else return HttpStatus.BAD_REQUEST;
        }).orElse(HttpStatus.BAD_REQUEST);

    }

}