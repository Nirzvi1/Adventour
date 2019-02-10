package com.adventour.adventour;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adventour.adventour.data.Challenge;
import com.adventour.adventour.data.Pair;

import java.util.HashMap;

public class ChallengeActivity extends AppCompatActivity {

    public static Challenge ch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        TextView title = (TextView) findViewById(R.id.title);
        TextView fieldNames = (TextView) findViewById(R.id.field_names);
        TextView fieldValues = (TextView) findViewById(R.id.field_values);
        TextView errormsg = (TextView) findViewById(R.id.errormsg);

        Button complete = (Button) findViewById(R.id.complete);

        title.setText(ch.getActivity());
        fieldNames.setText("City: \nVotes: \nLocation: \nDifficulty: ");
        fieldValues.setText(ch.getCity() + "\n" + ch.getVotes() + "\n" + Math.round(ch.getLocation().first * 10000) / 10000 + "°N " + Math.round(ch.getLocation().second * 10000) / 10000 + "°E\n" + ch.getDifficulty());

        complete.setEnabled(false);
        errormsg.setVisibility(View.INVISIBLE);

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("GPS Error", "No permissions for GPS Signal!");
            return;
        }

        final Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (Math.abs(ch.getLocation().first - loc.getLatitude()) < 0.001
                && Math.abs(ch.getLocation().second - loc.getLongitude()) < 0.001) {
            complete.setEnabled(true);
        } else {
            errormsg.setVisibility(View.VISIBLE);
        }

        if (Home.u.getCompleted().contains(ch.getChallengeId())) {
            complete.setEnabled(false);
            errormsg.setText("You have already completed this challenge!");
        }
    }

    public void vote(View v) {
        ch.setVotes(ch.getVotes() + 1);
        Database.writeChallenge(ch);
        findViewById(R.id.votebutton).setEnabled(false);
    }

    public void completeChallenge(View v) {
        Home.u.addCompleted(ch.getChallengeId());
        long pts = 0;
        if (Home.u.getPoints().containsKey(ch.getCity())) {
            pts = Home.u.getPoints().get(ch.getCity()).first;
        }
        Home.u.getPoints().put(ch.getCity(), new Pair<Long>(pts + ch.getDifficulty() * 10, 0l));

        findViewById(R.id.complete).setEnabled(false);
        Database.writeUser(Home.u);
        Home.instance.drawLists();
    }
}
