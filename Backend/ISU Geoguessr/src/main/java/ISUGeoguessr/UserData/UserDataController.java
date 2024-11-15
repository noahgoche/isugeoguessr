package ISUGeoguessr.UserData;


import java.util.List;

import ISUGeoguessr.Chat.Message;
import ISUGeoguessr.Chat.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//
import ISUGeoguessr.Stats.*;

@RestController
public class UserDataController {

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    StatsRepository statsRepository;

    @Autowired
    MessageRepository messageRepository;

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

    @GetMapping(path = "/users/{id}/messages")
    List<Message> getUserMessagesById(@PathVariable int id)
    {
        UserData userData = userDataRepository.findById(id);

        return userData.getMessageList();
    }

    @GetMapping(path = "users/password/{id}")
    String getPasswordById(@PathVariable int id)
    {
        UserData userData = userDataRepository.findById(id);
        if(userData == null)
            return "failed";

        return userData.getUserPassword();
    }

    @GetMapping(path = "users/Wins/{id}")
    int getWinsById(@PathVariable int id)
    {
        UserData userData = userDataRepository.findById(id);
        if(userData == null)
            return -1;

        return userData.getStatsList().get(0).getWins();
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

    @PutMapping(path = "/users/{id}/Stats/{statsId}")
    String assignStatsToUser(@PathVariable int id, @PathVariable int statsId) {
        UserData userData = userDataRepository.findById(id);
        Stats stats = statsRepository.findById(statsId);

        if (userData == null || stats == null)
        {
            return "Failed";
        }

        userData.addStats(stats);
        stats.setUserData(userData);
        stats.setUsername(userData.getUsername());

        userDataRepository.save(userData);
        statsRepository.save(stats);

        return "Success";
    }


    //Have to use form-data in postman to change password
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

    //Have to use form-data in postman to change @RequestParam
    @PutMapping(path = "/users/username/{id}")
    String updateUsernameById(@PathVariable int id, @RequestParam String newUsername)
    {
        UserData userData = userDataRepository.findById(id);

        if(userData == null)
        {
            return "failed";
        }

        userData.setUsername(newUsername);
        userDataRepository.save(userData);
        return "Username Updated";
    }

    //Have to use form-data in postman to change @RequestParam
    @PutMapping(path = "/users/email/{id}")
    String updateEmailById(@PathVariable int id, @RequestParam String newEmail)
    {
        UserData userData = userDataRepository.findById(id);

        if(userData == null)
        {
            return "failed";
        }

        userData.setUserEmail(newEmail);
        userDataRepository.save(userData);
        return "Email Updated";
    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUserById(@PathVariable int id){
        UserData userData = userDataRepository.findById(id);
        List<Stats> stats = userData.getStatsList();
        List<Message> messages = userData.getMessageList();

        for(int i =0; i < stats.size(); i++)
        {
            stats.get(i).setUserData(null);
            messages.get(i).setUserData(null);
        }
        userData.setStatsList(null);
        userData.setMessageList(null);

        userDataRepository.deleteById(id);
        return "deleted";
    }

}
