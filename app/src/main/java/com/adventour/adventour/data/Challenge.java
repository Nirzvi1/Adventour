package com.adventour.adventour.data;

import java.util.HashMap;

public class Challenge extends Document {

    //ctor when nothing is known about the user (null value)
    public Challenge() {
        put("challengeId", 0l);
        put("activity", "Nothing");
        put("city", "Ottawa");
        put("votes", 0);
        put("location", new Pair<Double>(0d, 0d));
        put("difficulty", 0);
    }

    public Challenge(long challengeId, String activity, String city, int difficulty) {
        this();
        put("challengeId", challengeId);
        put("activity", activity);
        put("city", city);
        put("difficulty", difficulty);
    }

    public Challenge(HashMap<String, Object> map) {
        this();
        putAll(map);
    }

    public long getChallengeId() {
        return (long) get("challengeId");
    }
    public void setChallengeId(long challengeId) {
        put("challengeId", challengeId);
    }

    public String getActivity() {
        return (String) get("activity");
    }
    public void setActivity(String activity) {
        put("activity", activity);
    }

    public String getCity() {
        return (String) get("city");
    }
    public void setCity(String city) {
        put("city", city);
    }

    public int getVotes() {
        return (int) get("votes");
    }
    public void setVotes(int votes) {
        put("votes", votes);
    }

    public double[] getLocation() {
        return (double[]) get("location");
    }
    public void setLocation(double[] location) {
        put("location", location);
    }

    public int difficulty() {
        return (int) get("difficulty");
    }
    public void setDifficulty(int difficulty) {
        put("difficulty", difficulty);
    }

}
