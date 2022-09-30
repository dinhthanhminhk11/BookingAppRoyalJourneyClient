package com.example.bookingapproyaljourney.model.map;

import java.util.List;

public class Leg {
    public Distance distance;
    public Duration duration;
    public String end_address;
    public EndLocation end_location;
    public String start_address;
    public StartLocation start_location;
    public List<Step> steps;
    public List<Object> traffic_speed_entry;
    public List<Object> via_waypoint;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public EndLocation getEnd_location() {
        return end_location;
    }

    public void setEnd_location(EndLocation end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public StartLocation getStart_location() {
        return start_location;
    }

    public void setStart_location(StartLocation start_location) {
        this.start_location = start_location;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Object> getTraffic_speed_entry() {
        return traffic_speed_entry;
    }

    public void setTraffic_speed_entry(List<Object> traffic_speed_entry) {
        this.traffic_speed_entry = traffic_speed_entry;
    }

    public List<Object> getVia_waypoint() {
        return via_waypoint;
    }

    public void setVia_waypoint(List<Object> via_waypoint) {
        this.via_waypoint = via_waypoint;
    }
}
