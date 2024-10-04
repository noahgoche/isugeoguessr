package ISUGeoguessr.Location;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ISUGeoguessr.User.User;

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
    private String locationCoords;

    @JsonIgnore
    @Lob
    private Blob image;

    //constructors

    public Location(String LocationName, String locationCoords)
    {
        this.LocationName = LocationName;
        this.locationCoords = locationCoords;
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
        return locationCoords;
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
