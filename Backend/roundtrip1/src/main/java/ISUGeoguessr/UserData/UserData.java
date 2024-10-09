package ISUGeoguessr.UserData;


import jakarta.persistence.*;

import ISUGeoguessr.Stats.Stats;

import java.util.ArrayList;
import java.util.List;

@Entity
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
    private List<Stats> stats;

    //Constructors

    public UserData(String username, String userEmail, String userPassword)
    {
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        stats = new ArrayList<>();
    }

    public UserData(){
    }

    //Accessors and mutators

    public int getUserID()
    {
        return id;
    }

    public String getUserName()
    {
        return username;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUsername(String newUsername)
    {
        username = newUsername;
    }

    public void setUserEmail(String newEmail)
    {
        userEmail = newEmail;
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword(String newPassword)
    {
        userPassword = newPassword;
    }

}
