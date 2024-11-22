package ISUGeoguessr.Stats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import ISUGeoguessr.UserData.UserData;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Entity
@Getter @Setter @NoArgsConstructor
public class Stats implements Comparator<Stats> {

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
     * @ManyToOne tells Springboot that multiple instances of Stats can map to one instance of OR multiple rows of the Stats table can map to one userdata row
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

    @Override
    public int compare(Stats stats1, Stats stats2)
    {
        return stats2.getTotalScore() - stats1.getTotalScore();
    }

}
