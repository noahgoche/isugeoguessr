package ISUGeoguessr.Location;

import java.sql.Blob;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Location {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String LocationName;
    private String LocationCoords;

    @JsonIgnore
    @Lob
    private Blob image;

    //constructors

    public Location(String LocationName, String LocationCoords)
    {
        this.LocationName = LocationName;
        this.LocationCoords = LocationCoords;
    }

    public Location(){
    }

    //accessors and mutators

    public int getLocationID()
    {
        return id;
    }

    public String getLocationName()
    {
        return LocationName;
    }

    public String getLocationCoords()
    {
        return LocationCoords;
    }

    public Blob getImage()
    {
        return image;
    }

    public void setLocationID(int id)
    {
        this.id = id;
    }

    public void setLocationName(String locationName)
    {
        LocationName = locationName;
    }

}
