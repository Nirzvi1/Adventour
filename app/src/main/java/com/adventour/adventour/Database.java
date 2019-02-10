package com.adventour.adventour;

import com.adventour.adventour.data.Badge;
import com.adventour.adventour.data.Challenge;
import com.adventour.adventour.data.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by FIXIT on 2019-02-09.
 */

public class Database {

    public static void write(DatabaseReference rf, String path, Object val) {
        rf.child(path).setValue(val);
    }

    public static void write(String path, Object val) {
        FirebaseDatabase.getInstance().getReference(path).setValue(val);
    }

    public static void writeUser(User u) {
        FirebaseDatabase.getInstance().getReference("users/user" + u.getUserId()).setValue(u);
    }

    public static void writeChallenge(Challenge c) {
        FirebaseDatabase.getInstance().getReference("challenges/challenge" + c.getChallengeId()).setValue(c);
    }

    public static void writeBadge(Badge b) {
        FirebaseDatabase.getInstance().getReference("badges/badge" + b.getBadgeId()).setValue(b);
    }

    public static HashMap<String, Object> read(DatabaseReference rf, String path) {
        final HashMap<String, Object> map = new HashMap();
        rf.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map.put(dataSnapshot.getKey(), dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return map;
    }

    public static User readUser(String userId) {
        return new User(read(FirebaseDatabase.getInstance().getReference(), "users/user" + userId));
    }

    public static Challenge readChallenge (long challengeId) {
        return new Challenge(read(FirebaseDatabase.getInstance().getReference(), "challenges/challenge" + challengeId));
    }

    public static Badge readBadge(int badgeId) {
        return new Badge(read(FirebaseDatabase.getInstance().getReference(), "badges/badge" + badgeId));
    }

}
