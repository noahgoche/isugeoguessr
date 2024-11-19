package ISUGeoguessr.Location;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.sql.SQLException;
import java.io.IOException;

import ISUGeoguessr.Stats.Stats;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    //String for the file path on the server
    private final static String IMAGE_FILEPATH = "/srv/images/";

    @Operation(summary = "Retrieves all Locations")
    @GetMapping(path = "/Location")
    List<Location> getAllLocations()
    {
        return locationRepository.findAll();
    }

    @Operation(summary = "Retrieves a specific Location by its id")
    @GetMapping(path = "/Location/{id}")
    Location findLocationById(@PathVariable int id){
        return locationRepository.findById(id);
    }

    @Operation(summary = "Retrieves a specific Location's coordinates by its id")
    @GetMapping(path = "/Location/Coordinates/{id}")
    String findCoordinatesById(@PathVariable int id)
    {
        return "latitude: " + locationRepository.findById(id).getLatitude() + ", longitude: " + locationRepository.findById(id).getLongitude();
    }

    @Operation(summary = "Retrieves a specific Location's image by its id")
    @GetMapping(path = "/Location/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] findImageById(@PathVariable int id) throws IOException{
        Location location = locationRepository.findById(id);
        File imageFile = new File(IMAGE_FILEPATH + location.getImageFileName());

        return Files.readAllBytes(imageFile.toPath());
    }

    @Operation(summary = "Creates a new Location")
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

    @Operation(summary = "Updates the specific Location's coordinates by its id")
    @PutMapping(path = "/Location/{id}/{newLat}/{newLong}")
    String updateLocationCoordsById(@PathVariable int id, @PathVariable double newLat, @PathVariable double newLong)
    {
        Location location = locationRepository.findById(id);
        if (location == null)
        {
            return "Failed";
        }
        location.setLatitude(newLat);
        location.setLongitude(newLong);
        locationRepository.save(location);
        return "Success";
    }

    @Operation(summary = "Deletes the specific Location by its id")
    @DeleteMapping(path = "/Location/{id}")
    String deleteLocationById(@PathVariable int id){

        locationRepository.deleteById(id);
        return "Deleted";
    }
}
