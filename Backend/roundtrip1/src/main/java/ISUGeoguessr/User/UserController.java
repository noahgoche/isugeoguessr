package ISUGeoguessr.User;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;




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
    String createUser(@RequestBody User user)
    {
        if(user == null)
        {
            return "Failed";
        }
        userRepository.save(user);
        return "Success";
    }

    //TODO find out why delete isn't working
    @DeleteMapping(path = "/users/{id}")
    String deleteUserById(@PathVariable int id){
        userRepository.deleteById(id);
        return "deleted";
    }

    //TODO fix. Password isn't changing after printing updated
    @PutMapping(path = "/users/password/{id}")
    String updatePasswordById(@PathVariable int id, @RequestBody String newPassword)
    {
        User user = userRepository.findById(id);

        if(user==null)
        {
            return "failed";
        }

        user.setUserPassword(newPassword);
        return "Password Updated";
    }

    @GetMapping(path = "users/password/{id}")
    String getPasswordById(@PathVariable int id)
    {
        User user = userRepository.findById(id);
        if(user == null)
            return "failed";

        return user.getUserPassword();
    }

}
