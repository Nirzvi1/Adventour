package com.adventour.adventour;

import android.util.Log;

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

    public static HashMap<String, Object> blockingRead(final String path) {

        final HashMap<String, Object> map = new HashMap<>();


        Thread r = new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase.getInstance().getReference().child(path).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        map.putAll(readDataSnapshot(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        r.start();


        return map;
    }

    public static HashMap<String, Object> read(final String path, final Callback dc) {
        final HashMap<String, Object> map = new HashMap<>();
        FirebaseDatabase.getInstance().getReference().child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dc.receive(readDataSnapshot(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return map;
    }

    public static HashMap<String, Object> readDataSnapshot(DataSnapshot d) {
        HashMap<String, Object> map = new HashMap<>();

        Iterable<DataSnapshot> it = d.getChildren();
        for (DataSnapshot c : it) {
            if (c.getChildrenCount() > 0) {
                map.put(c.getKey(), readDataSnapshot(c));
            } else {
                map.put(c.getKey(), c.getValue());
            }
        }

        return map;
    }


}
