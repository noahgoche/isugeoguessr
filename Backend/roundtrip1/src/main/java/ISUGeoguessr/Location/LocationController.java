package ISUGeoguessr.Location;

import java.util.List;
import java.sql.SQLException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    @GetMapping(path = "/Location")
    List<Location> getAllLocations()
    {
        return locationRepository.findAll();
    }

    @PostMapping(path = "/Location")
    String createLocation(@RequestBody Location location)
    {
        if(location == null)
        {
            return "Failure";
        }
        locationRepository.save(location);
        return "Success";
    }

    @GetMapping(path = "/Location/{id}")
    Location findLocationById(@PathVariable int id){
        return locationRepository.findById(id);
    }


    @DeleteMapping(path = "/Location/{id}")
    String deleteLocationById(@PathVariable int id){

        locationRepository.deleteById(id);
        return "Deleted";
    }
}
