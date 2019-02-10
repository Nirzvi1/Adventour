package com.adventour.adventour.data;

import java.util.HashMap;

public class Challenge extends Document {

    private static final long serialVersionUID = 7526472295622776147L;

    String[] fields = {"challengeId", "activity", "city", "votes",
            "location", "difficulty"};

    //ctor when nothing is known about the user (null value)
    public Challenge() {
        put("challengeId", 0l);
        put("activity", "Nothing?");
        put("city", "Ottawa");
        put("votes", 0l);
        put("location", new Pair<Double>(0d, 0d));
        put("difficulty", 0l);
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
        for (String f : fields) {
            if (f.equals("location") && map.containsKey(f)) {
                try {
                    put("location", convertFromPairDouble((HashMap<String, Double>) map.get("location")));
                } catch (ClassCastException e) {
                    put("location", convertFromPairLong((HashMap<String, Long>) map.get("location")));
                }
            } else {
                put(f, map.get(f));
            }
        }
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

    public long getVotes() {
        return (long) get("votes");
    }
    public void setVotes(long votes) {
        put("votes", votes);
    }

    public Pair<Double> getLocation() {
        return (Pair<Double>) get("location");
    }
    public void setLocation(Pair<Double> location) {
        put("location", location);
    }

    public long getDifficulty() {
        return (long) get("difficulty");
    }
    public void setDifficulty(long difficulty) {
        put("difficulty", difficulty);
    }

}
