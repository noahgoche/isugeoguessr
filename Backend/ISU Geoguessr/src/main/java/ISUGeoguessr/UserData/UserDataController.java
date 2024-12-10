package ISUGeoguessr.UserData;


import java.util.ArrayList;
import java.util.List;

import ISUGeoguessr.Chat.Message;
import ISUGeoguessr.Chat.MessageRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ISUGeoguessr.Stats.*;

@RestController
public class UserDataController {

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    StatsRepository statsRepository;

    @Autowired
    MessageRepository messageRepository;

    @Operation(summary = "Retrieves all users")
    @GetMapping(path = "/users")
    List<UserData> getAllUsers()
    {
        return userDataRepository.findAll();
    }

    @Operation(summary = "Retrieves a specific user by its id")
    @GetMapping(path = "/users/{id}")
    UserData getUserById(@PathVariable int id)
    {
        return userDataRepository.findById(id);
    }

    @Operation(summary = "Retrieves a specific user's messages by its id")
    @GetMapping(path = "/users/{id}/messages")
    List<Message> getUserMessagesById(@PathVariable int id)
    {
        UserData userData = userDataRepository.findById(id);

        return userData.getMessageList();
    }

    @Operation(summary = "Retrieves a specific user's password by its id")
    @GetMapping(path = "users/password/{id}")
    String getPasswordById(@PathVariable int id)
    {
        UserData userData = userDataRepository.findById(id);
        if(userData == null)
            return "failed";

        return userData.getUserPassword();
    }

    @Operation(summary = "Retrieves a specific user's wins by its id")
    @GetMapping(path = "users/Wins/{id}")
    int getWinsById(@PathVariable int id)
    {
        UserData userData = userDataRepository.findById(id);
        if(userData == null)
            return -1;

        return userData.getStatsList().get(0).getWins();
    }

    @Operation(summary = "Creates a new user")
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

    @Operation(summary = "Update the Stats object of a user by the user's id")
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
    @Operation(summary = "Updates a user's password by its id")
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
    @Operation(summary = "Updates a user's username by its id")
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
    @Operation(summary = "Updates a user's email by its id")
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

    @Operation(summary = "Deletes a specific user by its id")
    @DeleteMapping(path = "/users/{id}")
    String deleteUserById(@PathVariable int id){
        UserData userData = userDataRepository.findById(id);

        if(userData == null)
        {
            return "failed";
        }

        List<Stats> stats = userData.getStatsList();
        List<Message> messages = userData.getMessageList();

        userData.setStatsList(null);
        userData.setMessageList(null);

        if(!stats.isEmpty()) {
            for (Stats stat : stats) {
                stat.setUserData(null);
                statsRepository.save(stat);
                statsRepository.deleteById(stat.getId());
            }
        }

        if(!messages.isEmpty()) {
            for (Message message : messages) {
                message.setUserData(null);
                messageRepository.save(message);
                messageRepository.deleteById(message.getId());
            }
        }

        //commented section was for deleting items in stat and message table that still had the user Id when it was null in the actual user and couldn't be referenced by user directly
//        messages = new ArrayList<>(messages);
//        messages.addAll(messageRepository.findAll());
//        for(Message message: messages)
//        {
//            if(message.getUserData() != null && message.getUserData().getId() == userData.getId())
//            {
//                message.setUserData(null);
//                messageRepository.save(message);
//                messageRepository.deleteById(message.getId());
//            }
//        }
//
//
//        stats = new ArrayList<>(stats);
//        stats.addAll(statsRepository.findAll());
//        for(Stats stat: stats)
//        {
//            if(stat.getUserData() != null && stat.getUserData().getId() == userData.getId())
//            {
//                stat.setUserData(null);
//                statsRepository.save(stat);
//                statsRepository.deleteById(stat.getId());
//            }
//        }

        userDataRepository.deleteById(id);
        return "deleted";
    }

}
