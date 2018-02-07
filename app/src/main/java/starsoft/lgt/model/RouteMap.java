package starsoft.lgt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteMap {

    @SerializedName("startStation")
    @Expose
    public Station startStation;
    @SerializedName("endStation")
    @Expose
    public Station endStation;
    @SerializedName("routeStations")
    @Expose
    public List<Station> routeStations = null;
    @SerializedName("routeColor")
    @Expose
    public String routeColor;
    @SerializedName("strokeWeight")
    @Expose
    public int strokeWeight;
    @SerializedName("strokeOpacity")
    @Expose
    public double strokeOpacity;

    public RouteMap(Station startStation, Station endStation, List<Station> routeStations, String routeColor, int strokeWeight, double strokeOpacity) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.routeStations = routeStations;
        this.routeColor = routeColor;
        this.strokeWeight = strokeWeight;
        this.strokeOpacity = strokeOpacity;
    }

}