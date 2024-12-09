package ISUGeoguessr.UserData;

import ISUGeoguessr.Chat.Message;
import jakarta.persistence.*;

import ISUGeoguessr.Stats.Stats;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class UserData {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String userEmail;
    private String userPassword;

    /*
     * @OneToMany tells springboot that one instance of UserData can map to multiple instances of Stats OR one user row can map to multiple rows of the stats table
     */
    @OneToMany
    private List<Stats> statsList;

    @OneToMany (fetch = FetchType.EAGER)
    private List<Message> messageList;

    public UserData(String username, String userEmail, String userPassword)
    {
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        statsList = new ArrayList<>();
        messageList = new ArrayList<>();
    }

    public UserData()
    {
        statsList = new ArrayList<>();
        messageList = new ArrayList<>();
    }

    public void addStats(Stats stats)
    {
        statsList.add(stats);
    }

    public void addMessages(Message message)
    {
        messageList.add(message);
    }



}
