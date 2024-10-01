package Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UserID;
    private String username;
    private String userEmail;
    private String userPassword;
    //playerStats retrieved from a separate class/table


    //Constructor(s)

    public User(int UserID, String username, String userEmail, String userPassword)
    {
        this.UserID = UserID;
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }


    //Accessors and mutators

    public int getUserID()
    {
        return UserID;
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

    public void setUserPassword(String newPassword)
    {
        userPassword = newPassword;
    }

}
