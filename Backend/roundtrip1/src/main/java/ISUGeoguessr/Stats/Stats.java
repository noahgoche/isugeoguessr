package ISUGeoguessr.Stats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import ISUGeoguessr.UserData.UserData;
import org.hibernate.bytecode.enhance.spi.EnhancementInfo;

@Entity
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

    /*
     * @ManyToOne tells springboot that multiple instances of Stats can map to one instance of OR multiple rows of the Stats table can map to one userdata row
     * @JoinColumn specifies the ownership of the key i.e. The Stats table will contain a foreign key from the UserData table and the column name will be userdata_id
     * @JsonIgnore is to assure that there is no infinite loop while returning either userdata/stats objects
     */
    @ManyToOne
    @JoinColumn(name = "userdata_id")
    @JsonIgnore
    private UserData userData;

    //Constructors

    public Stats(String gameMode)
    {
        this.gameMode = gameMode;

    }

    public Stats(){}

    //Accessors and mutators

    public int getStatsId(){
        return id;
    }

    public void setStatsId(int id)
    {
        this.id = id;
    }

    public int getWins()
    {
        return wins;
    }

    public void setWins(int wins)
    {
        this.wins = wins;
    }

    public int getTotalScore()
    {
        return totalScore;
    }

    public void setTotalScore(int totalScore)
    {
        this.totalScore = totalScore;
    }

    public float getTimePlayed()
    {
        return timePlayed;
    }

    public void setTimePlayed(float timePlayed)
    {
        this.timePlayed = timePlayed;
    }

    public int getGamesPlayed()
    {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed)
    {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesLost()
    {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost)
    {
        this.gamesLost = gamesLost;
    }

    public String getGameMode()
    {
        return gameMode;
    }

    public void setGameMode(String gameMode)
    {
        this.gameMode = gameMode;
    }

}
