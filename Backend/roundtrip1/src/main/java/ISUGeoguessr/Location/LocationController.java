package ISUGeoguessr.Location;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.sql.SQLException;
import java.io.IOException;

import ISUGeoguessr.Stats.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

   // private static String directory = "/tmp/";


    @GetMapping(path = "/Location")
    List<Location> getAllLocations()
    {
        return locationRepository.findAll();
    }

    @GetMapping(path = "/Location/{id}")
    Location findLocationById(@PathVariable int id){
        return locationRepository.findById(id);
    }

    @GetMapping(path = "/Location/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] findImageFileById(@PathVariable int id) throws IOException{
        Location location = locationRepository.findById(id);
        File imageFile = new File(location.getImageFilePath());

        return Files.readAllBytes(imageFile.toPath());
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

    @PutMapping(path = "/Location/{id}")
    String updateLocationCoordsById(@PathVariable int id, @PathVariable String newCoords)
    {
        Location location = locationRepository.findById(id);
        if (location == null)
        {
            return "Failed";
        }
        location.setLocationCoords(newCoords);
        locationRepository.save(location);
        return "Success";
    }

    @PutMapping(path = "/image/{id}")
    String updateImageFileById(@PathVariable int id, @PathVariable String fileName)
    {
        Location location = locationRepository.findById(id);
        if (location == null)
        {
            return "Failed";
        }
        location.setImageFilePath(fileName);
        locationRepository.save(location);
        return "Success";
    }

    @DeleteMapping(path = "/Location/{id}")
    String deleteLocationById(@PathVariable int id){

        locationRepository.deleteById(id);
        return "Deleted";
    }
}
