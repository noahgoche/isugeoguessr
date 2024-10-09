package ISUGeoguessr.Stats;

import java.util.List;

import ISUGeoguessr.UserData.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatsController {

    @Autowired
    StatsRepository statsRepository;

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

    @DeleteMapping(path = "/Stats/{id}")
    String deleteById(@PathVariable int id)
    {
        if(statsRepository.findById(id) == null)
        {
            return "failed";
        }

        statsRepository.deleteById(id);
        return "Deleted";
    }

    @PutMapping(path = "/Stats/wins/{id}/{wins}")
    String updateWinsById(@PathVariable int id, @PathVariable int wins)
    {
        Stats stats = statsRepository.findById(id);
        if (stats == null)
        {
            return "Failed";
        }

        stats.setWins(wins);
        return "Success";
    }

}
