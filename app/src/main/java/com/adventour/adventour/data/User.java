package com.adventour.adventour.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class User extends Document {

    //ctor when nothing is known about the user (null value)
    public User() {
        put("userId", "000000");
        put("userName", "Jane Doe");
        put("password", "123456");
        put("authToken", "000000");
        put("location", new Pair(0d,0d));
        put("isDiscoverable", false);
        put("points", new HashMap<String, Pair<Double>>());
        put("badges", new ArrayList<Integer>());
        put("completed", new ArrayList<Long>());
        put("created", new ArrayList<Long>());
    }

    public User(String userId, String userName, String password, String authToken) {
        this();
        put("userId", userId);
        put("userName", userName);
        put("password", password);
        put("authToken", authToken);
    }

    public User(HashMap<String, Object> map) {
        this();
        putAll(map);
    }

    public String getUserId() {
        return (String) get("userId");
    }
    public void setUserId(String userId) {
        put("userId", userId);
    }

    public String getUserName() {
        return (String) get("userName");
    }
    public void setUserName(String userName) {
        put("userName", userName);
    }

    public String getPassword() {
        return (String) get("password");
    }
    public void setPassword(String password) {
        put("password", password);
    }

    public String getAuthToken() {
        return (String) get("authToken");
    }
    public void setAuthToken(String authToken) {
        put("authToken", authToken);
    }

    public double[] getLocation() {
        return (double[]) get("location");
    }
    public void setLocation(double[] location) {
        put("location", location);
    }

    public boolean isDiscoverable() {
        return (boolean) get("isDiscoverable");
    }
    public void setDiscoverable(boolean discoverable) {
        put("isDiscoverable", discoverable);
    }
    
    public HashMap<String, double[]> getPoints() {
        return (HashMap<String, double[]>) get("points");
    }
    public void putPoints(String city, double[] points) {
        getPoints().put(city, points);
    }
    public void addToPoints(String city, double[] points) {
        double[] cur_pts = getPoints().get(city); 
        getPoints().put(city, new double[] {cur_pts[0], cur_pts[1]});
    }

    public ArrayList<Integer> getBadges() {
        return (ArrayList<Integer>) get("badges");
    }
    public void addBadge(int badge) {
        ((ArrayList<Integer>) get("badges")).add(badge);
    }
    public void removeBadge(int badge) {
        ((ArrayList<Integer>) get("badges")).remove((Integer) badge);
    }

    public ArrayList<Long> getCompleted() {
        return (ArrayList<Long>) get("completed");
    }
    public void addCompleted(long completed) {
        ((ArrayList<Long>) get("completed")).add(completed);
    }
    public void removeCompleted(long completed) {
        ((ArrayList<Long>) get("completed")).remove((Long) completed);
    }

    public ArrayList<Long> getCreated() {
        return (ArrayList<Long>) get("created");
    }
    public void addCreated(long created) {
        ((ArrayList<Long>) get("created")).add(created);
    }
    public void removeCreated(long created) {
        ((ArrayList<Long>) get("created")).remove((Long) created);
    }
    
}
