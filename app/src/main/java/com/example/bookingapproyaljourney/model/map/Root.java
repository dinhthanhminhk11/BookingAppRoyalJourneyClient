package com.example.bookingapproyaljourney.model.map;




import java.util.List;

public class Root {
    private List<GeocodedWaypoint> geocoded_waypoints;
    private List<Route> routes;
    private String status;

    public List<GeocodedWaypoint> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(List<GeocodedWaypoint> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
