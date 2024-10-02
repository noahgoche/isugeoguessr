package Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/users")
    List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id)
    {
        return userRepository.findById(id);
    }

    @PostMapping(path = "/users")
    void createUser(@RequestBody User user)
    {
        userRepository.save(user);
    }

    @DeleteMapping(path = "/users/{id}")
    void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PutMapping(path = "/users/{id}")
    void updatePassword(@PathVariable int id, @RequestBody String newPassword)
    {
        User user = userRepository.findById(id);
        user.setUserPassword(newPassword);
    }

}
