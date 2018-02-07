package starsoft.lgt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PinStation extends Station {
    @SerializedName("description")
    @Expose
    public String description;

    public PinStation(String name, String description, double longitude, double latitude) {
        super(name, longitude, latitude);
        this.description = description;
    }
}
