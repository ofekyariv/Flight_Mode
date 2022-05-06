package com.example.flight_mode;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FlightListActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    ListView lvFlights;
    ArrayList<Flight> flights;
    FlightsAdapter adapter;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);

        lvFlights = findViewById(R.id.lvFlights);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loadin Please Wait...");
        progressDialog.show();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        DatabaseReference tripsDB = firebaseDatabase.getReference("Users/"+firebaseAuth.getUid()+"/Flights");
        flights = new ArrayList<Flight>();
        tripsDB.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flights.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Flight flight = new Flight();
                    flight.setCountry(data.child("country").getValue().toString());
                    flight.setCity(data.child("city").getValue().toString());
                    flight.setHotel(data.child("hotel").getValue().toString());
                    flight.setDescription(data.child("description").getValue().toString());
                    flight.setFlightPrice(Integer.parseInt(data.child("flightPrice").getValue().toString()));
                    flight.setKey(data.getKey());
                    flights.add(flight);
                }
                adapter = new FlightsAdapter(FlightListActivity.this, flights);
                lvFlights.setAdapter(adapter);
                lvFlights.setOnItemLongClickListener(FlightListActivity.this);
                progressDialog.dismiss();
                if(flights.isEmpty()){
                    Toast.makeText(FlightListActivity.this, "No Planned Flights", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FlightListActivity.this, "Unable to Retrieve data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete Flight");
        alert.setMessage("Are you sure you want to delete the flight?");
        alert.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                progressDialog.show();
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                String key = flights.get(position).getKey();
                DatabaseReference tripsDB = firebaseDatabase.getReference("Users/"+firebaseAuth.getUid()+"/Flights");
                tripsDB.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        adapter.notifyDataSetChanged();
                    }
                });
                dialogInterface.dismiss();
            }
        });

        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.create();
        alert.show();
        //adapter.notifyDataSetChanged();
        return true;
    }
}