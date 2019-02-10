package com.adventour.adventour.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class User extends Document {

    String[] fields = {"userId", "userName", "password", "authToken",
                        "location", "isDiscoverable", "points", "badges", "completed", "created"};

    //ctor when nothing is known about the user (null value)
    public User() {
        put("userId", "000000");
        put("userName", "Jane Doe");
        put("password", "123456");
        put("authToken", "000000");
        put("location", new Pair(0d,0d));
        put("isDiscoverable", false);
        put("points", new HashMap<String, Pair<Long>>());
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
        for (String f : fields) {
            if (f.equals("badges") && map.containsKey(f)) {
               put("badges", convertFromInt((HashMap<String, Integer>) map.get("badges")));
            } else if (f.equals("completed") && map.containsKey(f)) {
                put("completed", convertFromLong((HashMap<String, Long>) map.get("completed")));
            } else if (f.equals("created") && map.containsKey(f)) {
                put("created", convertFromLong((HashMap<String, Long>) map.get("created")));
            } else if (f.equals("location") && map.containsKey(f)) {
                try {
                    put("location", convertFromPairDouble((HashMap<String, Double>) map.get("location")));
                } catch (ClassCastException e) {
                    put("location", convertFromPairLong((HashMap<String, Long>) map.get("location")));
                }
            } else if (f.equals("points") && map.containsKey(f)) {
                HashMap<String, HashMap<String, Long>> m = (HashMap<String, HashMap<String, Long>>) map.get(f);
                HashMap<String, Pair<Long>> newM = new HashMap<>();
                Set<Entry<String, HashMap<String, Long>>> entries = m.entrySet();
                for (Entry<String, HashMap<String, Long>> e : entries) {
                    newM.put(e.getKey(), new Pair<Long>(e.getValue().get("first"), e.getValue().get("second")));
                }
                put("points", newM);
            } else {
                put(f, map.get(f));
            }
        }
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
    
    public HashMap<String, Pair<Long>> getPoints() {
        return (HashMap<String, Pair<Long>>) get("points");
    }
    public void putPoints(String city, Pair<Long> p) {
        getPoints().put(city, p);
    }
    public void addToPoints(String city, Pair<Long> p) {
        Pair<Long> cur_pts = getPoints().get(city);
        getPoints().put(city, new Pair<Long>(cur_pts.first + p.first, cur_pts.second + p.second));
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
        ((ArrayList<Long>) get("completed")).remove(completed);
    }

    public ArrayList<Long> getCreated() {
        return (ArrayList<Long>) get("created");
    }
    public void addCreated(long created) {
        ((ArrayList<Long>) get("created")).add(created);
    }
    public void removeCreated(long created) {
        ((ArrayList<Long>) get("created")).remove(created);
    }
    
}
