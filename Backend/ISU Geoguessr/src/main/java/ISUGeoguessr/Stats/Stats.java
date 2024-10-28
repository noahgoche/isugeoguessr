package ISUGeoguessr.Stats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import ISUGeoguessr.UserData.UserData;
import org.hibernate.bytecode.enhance.spi.EnhancementInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter @Setter @NoArgsConstructor
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int totalScore;
    private float timePlayed;
    private int wins;
    private int gamesPlayed;
    private int gamesLost;
    private String gameMode;
    private String username;

    /*
     * @ManyToOne tells springboot that multiple instances of Stats can map to one instance of OR multiple rows of the Stats table can map to one userdata row
     * @JoinColumn specifies the ownership of the key i.e. The Stats table will contain a foreign key from the UserData table and the column name will be userdata_id
     * @JsonIgnore is to assure that there is no infinite loop while returning either userdata/stats objects
     */
    @ManyToOne
    @JoinColumn(name = "userdata_id")
    @JsonIgnore
    private UserData userData;


    public Stats(String gameMode)
    {
        this.gameMode = gameMode;

    }


}
