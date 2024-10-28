package ISUGeoguessr.Stats;

import java.util.List;

import ISUGeoguessr.UserData.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatsController {

    @Autowired
    StatsRepository statsRepository;

    @Autowired
    UserDataRepository userDataRepository;

    @GetMapping(path = "/Stats")
    List<Stats> getAllStatsInfo()
    {
        return statsRepository.findAll();
    }

    @GetMapping(path = "/Stats/{id}")
    Stats getStatsById(@PathVariable int id)
    {
        return statsRepository.findById(id);
    }

    @PostMapping(path = "/Stats")
    String createStatsObject(@RequestBody Stats stats)
    {
        if(stats == null)
        {
            return "Failed";
        }

        statsRepository.save(stats);
        return "Success";
    }

    @GetMapping(path = "/Stats/{id}/totalScore")
    int getTotalScoreById(@PathVariable int id)
    {
        Stats stats = statsRepository.findById(id);
        if(stats == null)
        {
            return -1;
        }
        return stats.getTotalScore();
    }

    @GetMapping(path = "/Stats/{id}/timePlayed")
    float getTimePlayedById(@PathVariable int id)
    {
        Stats stats = statsRepository.findById(id);
        if(stats == null)
        {
            return -1;
        }
        return stats.getTimePlayed();
    }

    @GetMapping(path = "/Stats/{id}/wins")
    int getWinsById(@PathVariable int id)
    {
        Stats stats = statsRepository.findById(id);
        if(stats == null)
        {
            return -1;
        }
        return stats.getTotalScore();
    }

    @GetMapping(path = "/Stats/{id}/gamesPlayed")
    int getGamesPlayedById(@PathVariable int id)
    {
        Stats stats = statsRepository.findById(id);
        if(stats == null)
        {
            return -1;
        }
        return stats.getGamesPlayed();
    }

    @GetMapping(path = "/Stats/{id}/losses")
    int getLossesById(@PathVariable int id)
    {
        Stats stats = statsRepository.findById(id);
        if(stats == null)
        {
            return -1;
        }
        return stats.getGamesLost();
    }

    @GetMapping(path = "/Stats/{id}/gameMode")
    String getModeById(@PathVariable int id)
    {
        Stats stats = statsRepository.findById(id);
        if(stats == null)
        {
            return "Failed: null Stats object";
        }
        return stats.getGameMode();
    }

    @PutMapping(path = "/Stats/{id}/totalScore/{totalScore}")
    String updateTotalScoreById(@PathVariable int id, @PathVariable int totalScore)
    {
        Stats stats = statsRepository.findById(id);
        if (stats == null)
        {
            return "Failed";
        }
        stats.setTotalScore(totalScore);
        statsRepository.save(stats);
        return "Success";
    }

    @PutMapping(path = "/Stats/{id}/timePlayed/{timePlayed}")
    String updateTimePlayedById(@PathVariable int id, @PathVariable float timePlayed)
    {
        Stats stats = statsRepository.findById(id);
        if (stats == null)
        {
            return "Failed";
        }
        stats.setTimePlayed(timePlayed);
        statsRepository.save(stats);
        return "Success";
    }

    @PutMapping(path = "/Stats/{id}/wins/{wins}")
    String updateWinsById(@PathVariable int id, @PathVariable int wins)
    {
        Stats stats = statsRepository.findById(id);
        if (stats == null)
        {
            return "Failed";
        }
        stats.setWins(wins);
        statsRepository.save(stats);
        return "Success";
    }

    @PutMapping(path = "/Stats/{id}/gamesPlayed/{gamesPlayed}")
    String updateGamesPlayedById(@PathVariable int id, @PathVariable int gamesPlayed)
    {
        Stats stats = statsRepository.findById(id);
        if (stats == null)
        {
            return "Failed";
        }
        stats.setGamesPlayed(gamesPlayed);
        statsRepository.save(stats);
        return "Success";
    }

    @PutMapping(path = "/Stats/{id}/losses/{losses}")
    String updateLossesById(@PathVariable int id, @PathVariable int losses)
    {
        Stats stats = statsRepository.findById(id);
        if (stats == null)
        {
            return "Failed";
        }
        stats.setGamesLost(losses);
        statsRepository.save(stats);
        return "Success";
    }

    @DeleteMapping(path = "/Stats/{id}")
    String deleteById(@PathVariable int id)
    {
        Stats stats = statsRepository.findById(id);

        if(statsRepository.findById(id) == null)
        {
            return "failed";
        }
        UserData userData = stats.getUserData();
        userData.setStatsList(null);
        stats.setUserData(null);

        statsRepository.deleteById(id);
        return "Deleted";
    }

}
