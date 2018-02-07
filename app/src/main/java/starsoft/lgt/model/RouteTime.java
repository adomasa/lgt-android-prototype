package starsoft.lgt.model;

public class RouteTime {
    public final String departureTime;
    public final String arrivalTime;
    public final String duration;

    public RouteTime(String departureTime, String arrivalTime, String duration) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
    }

}