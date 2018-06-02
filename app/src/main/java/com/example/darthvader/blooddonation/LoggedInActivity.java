package com.example.darthvader.blooddonation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoggedInActivity extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    RecyclerViewAdapter adapter;
    RecyclerView view;
    List<User> userList = new ArrayList<>();
    List<String> bgList = new ArrayList<>();
    SearchView searchView;
    ProgressDialog dialog;
    Session session;

    public void logout() {
        dialog.show();
        new CountDownTimer(5000, 5000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                dialog.dismiss();
                session.setLoggedIn(false);
                Intent intent = new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Blood Donation");
        toolbar.setTitleTextColor(Color.BLACK);
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        adapter = new RecyclerViewAdapter(this, userList);
        searchView = findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(true);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        dialog = new ProgressDialog(LoggedInActivity.this);
        dialog.setTitle("Logging out");
        dialog.setMessage("Loading...");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        view = findViewById(R.id.recyclerView);
        view.setLayoutManager(layoutManager);
        session = new Session(this);

        if(!session.loggedIn())
        {
            Intent intent = new Intent(LoggedInActivity.this, MainActivity.class);
            startActivity(intent);
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (!user.getEmail().equals(DBUser.dbUser.getEmail())) {
                        userList.add(user);
                        bgList.add(user.getBloodGroup());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(listener);
        view.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }
}
