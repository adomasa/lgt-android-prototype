package starsoft.lgt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Station {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("longitude")
    @Expose
    public double longitude;
    @SerializedName("latitude")
    @Expose
    public double latitude;

    public Station(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }


}