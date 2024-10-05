package ISUGeoguessr.UserData;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserDataController {

    @Autowired
    UserDataRepository userDataRepository;

    @GetMapping(path = "/users")
    List<UserData> getAllUsers()
    {
        return userDataRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    UserData getUserById(@PathVariable int id)
    {
        return userDataRepository.findById(id);
    }

    @PostMapping(path = "/users")
    String createUser(@RequestBody UserData userData)
    {
        if(userData == null)
        {
            return "Failed";
        }
        userDataRepository.save(userData);
        return "Success";
    }

    //TODO Fix? delete doesn't work. Should work once connected to db
    @DeleteMapping(path = "/users/{id}")
    String deleteUserById(@PathVariable int id){
        userDataRepository.deleteById(id);
        return "deleted";
    }

    //TODO Fix? have to use form-data currently in postman to change password
    @PutMapping(path = "/users/password/{id}")
    String updatePasswordById(@PathVariable int id, @RequestParam String newPassword)
    {
        UserData userData = userDataRepository.findById(id);

        if(userData == null)
        {
            return "failed";
        }

        userData.setUserPassword(newPassword);
        userDataRepository.save(userData);
        return "Password Updated";
    }

    @GetMapping(path = "users/password/{id}")
    String getPasswordById(@PathVariable int id)
    {
        UserData userData = userDataRepository.findById(id);
        if(userData == null)
            return "failed";

        return userData.getUserPassword();
    }

}
