package pl.azurix.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired // This means to get the bean called userRepository
    private UserRepository usersRepository;

    @RequestMapping("/register")
    @ResponseBody
    public String register(@RequestParam String action, @RequestParam String login, @RequestParam String password, @RequestParam String email) {
        if(action.equals("register")) {
            User usr = new User(login, password, email);
            usersRepository.save(usr);
            return "done";
        }else return "error, no action";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Optional<Object> login(@RequestParam String action, @RequestParam String login, @RequestParam String password) {
        if(action.equals("login")) {
            Optional op= Optional.of(usersRepository.findById(usersRepository.login(login, password)));
            return op;
        }else return Optional.of("error, no action");
    }
}