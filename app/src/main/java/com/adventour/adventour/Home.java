package com.adventour.adventour;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.adventour.adventour.data.Challenge;
import com.adventour.adventour.data.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        u = Database.readUser("000000");
        u.addCompleted(0);
        u.addCreated(1);
        Database.writeUser(u);

        ArrayList<Long> u_com = u.getCompleted();
        ArrayList<String> c_com = new ArrayList<>();

        for (Long m : u_com) {
            c_com.add(Database.readChallenge(m).getActivity());
        }

        ArrayList<Long> u_cre = u.getCreated();
        ArrayList<String> c_cre = new ArrayList<>();

        for (Long m : u_cre) {
            c_cre.add(Database.readChallenge(m).getActivity());
        }

        ArrayAdapter<String> completed = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, c_com);

        ListView chall_compl = (ListView) findViewById(R.id.list_completed);
        chall_compl.setAdapter(completed);


        ArrayAdapter<String> created = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, c_cre);

        ListView chall_create = (ListView) findViewById(R.id.list_created);
        chall_create.setAdapter(created);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void explore(View v) {
        Intent explore = new Intent(this, Explore.class);
        startActivity(explore);
    }

}
