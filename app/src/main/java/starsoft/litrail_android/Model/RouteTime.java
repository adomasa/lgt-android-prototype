package starsoft.litrail_android.Model;


import java.text.SimpleDateFormat;

/**
 * Created by Ramu on 01/10/2017.
 */

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