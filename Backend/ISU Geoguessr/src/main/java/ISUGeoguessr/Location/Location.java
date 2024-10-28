package ISUGeoguessr.Location;

import java.sql.Blob;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @NoArgsConstructor
public class Location {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String LocationName;
    private String imageFileName;
    private double latitude;
    private double longitude;


    public Location(String LocationName, double latitude, double longitude, String imageFileName)
    {
        this.LocationName = LocationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageFileName = imageFileName;
    }

}
