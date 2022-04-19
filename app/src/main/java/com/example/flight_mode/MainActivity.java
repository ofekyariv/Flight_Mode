package com.example.flight_mode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnExplore, btnPlanAFlight, btnMyPlannedFlights;

    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExplore = (Button) findViewById(R.id.btnExplore);
        btnExplore.setOnClickListener(this);
        btnPlanAFlight = (Button) findViewById(R.id.btnPlanAFlight);
        btnPlanAFlight.setOnClickListener(this);
        btnMyPlannedFlights = (Button) findViewById(R.id.btnMyPlannedFlights);
        btnMyPlannedFlights.setOnClickListener(this);

        Intent service=new Intent(this, MusicService.class);
        startService(service);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new BroadcastCharging();
        IntentFilter ifilter = new IntentFilter();
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED);
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receiver, ifilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (LoginActivity.email.equals("admin@gmail.com")) {
            getMenuInflater().inflate(R.menu.menu_main_admin, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_share) // identify item pressed
        {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Try Flight_Mode Today!");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        if(item.getItemId() == R.id.action_logout){
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            SharedPreferences sharedPreferences = getSharedPreferences("login", 0);
            SharedPreferences.Editor editor;
            editor = sharedPreferences.edit();
            editor.putBoolean("saveLogin", false);
            editor.apply();
            Intent intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v==btnExplore){
            Intent intent=new Intent(this, ExploreActivity.class);
            startActivity(intent);
        }
        if(v==btnPlanAFlight){
            Intent intent=new Intent(this, FlightSearchActivity.class);
            startActivity(intent);
        }
        if(v==btnMyPlannedFlights){
            Intent intent=new Intent(this, FlightListActivity.class);
            startActivity(intent);
        }
    }
}
