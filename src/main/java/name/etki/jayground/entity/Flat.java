package name.etki.jayground.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
@Entity
@Table(name = "flat")
public class Flat implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    @Min(1)
    private int floor;
    @Column
    @Min(5)
    private double space;
    @Column
    @Min(-90)
    @Max(90)
    @NotNull
    private Double latitude;
    @Column
    @Min(-180)
    @Max(180)
    @NotNull
    private Double longitude;
    @Column(name = "depiction")
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getSpace() {
        return space;
    }

    public void setSpace(double space) {
        this.space = space;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Location getLocation() {
        Location location = new Location();
        location.setLatitude(this.latitude);
        location.setLongitude(this.longitude);
        return location;
    }
    
    public void setLocation(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
