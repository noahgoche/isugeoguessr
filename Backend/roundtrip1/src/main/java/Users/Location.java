package Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
public class Location {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int LocationID;

    @JsonIgnore
    @Lob
    private Blob image;



}
