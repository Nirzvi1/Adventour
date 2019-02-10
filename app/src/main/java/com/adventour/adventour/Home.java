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
import android.widget.AdapterView;
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
import java.util.HashMap;

public class Home extends AppCompatActivity {

    public static User u;
    private int i = 0;

    public static Home instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        instance = this;

        drawLists();

    }

    public void drawLists() {

        Database.read("users/user000000", new Callback() {

            @Override
            public void receive(Object m) {
                HashMap<String, Object> map = (HashMap<String, Object>) m;
                Log.i("MAP", map.toString());
                u = new User(map);

                final ArrayList<Long> u_com = u.getCompleted();
                final ArrayList<String> c_com = new ArrayList<>();
                final ArrayList<Challenge> ch_com = new ArrayList<>();

                Database.read("challenges/challenge" + u_com.get(u_com.size() - 1), genIterativeCallback(u_com.size() - 1, new Callback() {

                    @Override
                    public void receiveIterative(Object o, int i) {
                        HashMap<String, Object> mp = ((HashMap<String, Object>) ((Object[]) o)[0]);
                        Callback next = ((Callback) ((Object[]) o)[1]);

                        c_com.add((String) (mp.get("activity")));
                        ch_com.add(new Challenge(mp));
                        if (i > 0) {
                            Database.read("challenges/challenge" + u_com.get(i - 1), next);
                        } else {

                            final ArrayList<Long> u_cre = u.getCreated();
                            final ArrayList<String> c_cre = new ArrayList<>();
                            final ArrayList<Challenge> ch_cre = new ArrayList<>();

                            Database.read("challenges/challenge" + u_cre.get(u_cre.size() - 1), genIterativeCallback(u_cre.size() - 1, new Callback() {
                                @Override
                                public void receiveIterative(Object o, int i) {
                                    HashMap<String, Object> ma = ((HashMap<String, Object>) ((Object[]) o)[0]);
                                    Callback next = ((Callback) ((Object[]) o)[1]);

                                    c_cre.add((String) (ma.get("activity")));
                                    ch_cre.add(new Challenge(ma));
                                    if (i > 0) {
                                        Database.read("challenges/challenge" + u_com.get(i - 1), next);
                                    } else {

                                        Home.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                ((TextView) findViewById(R.id.name)).setText(u.getUserName());
                                                ((TextView) findViewById(R.id.points)).setText("Points: " + u.getPoints().get("Ottawa").first);

                                                ArrayAdapter<String> completed = new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1, android.R.id.text1, c_com);

                                                ListView chall_compl = (ListView) findViewById(R.id.list_completed);
                                                chall_compl.setAdapter(completed);
                                                chall_compl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        Intent ch = new Intent(Home.this, ChallengeActivity.class);
                                                        ChallengeActivity.ch = ch_com.get(position);
                                                        startActivity(ch);
                                                    }
                                                });

                                                ArrayAdapter<String> created = new ArrayAdapter<String>(Home.this, android.R.layout.simple_list_item_1, android.R.id.text1, c_cre);

                                                ListView chall_create = (ListView) findViewById(R.id.list_created);
                                                chall_create.setAdapter(created);
                                                chall_create.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        Intent ch = new Intent(Home.this, ChallengeActivity.class);
                                                        ChallengeActivity.ch = ch_cre.get(position);
                                                        startActivity(ch);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            }));
                        }
                    }
                }));
            }
        });
    }

    public static Callback genIterativeCallback(final int i, final Callback step) {
        return new Callback() {
            @Override
            public void receive(Object o) {
                step.receiveIterative(new Object[]{o, genIterativeCallback(i - 1, step)}, i);
            }
        };
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
