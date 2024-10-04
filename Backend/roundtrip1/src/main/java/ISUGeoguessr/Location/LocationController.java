package ISUGeoguessr.Location;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import ISUGeoguessr.User.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    void deleteLocationById(@PathVariable int id){
        locationRepository.deleteById(id);
    }
}
