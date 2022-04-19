package com.example.flight_mode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FlightSearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener{
    Button btnBooking, btnSaveFlight;
    AutoCompleteTextView etCountry;
    EditText etCity, etHotel, etDescription;
    ArrayAdapter<String> adapter;
    Flight flight;
    ArrayList<Flight> flights;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_search);
        btnBooking = (Button) findViewById(R.id.btnBooking);
        btnBooking.setOnClickListener(this);
        btnSaveFlight = (Button) findViewById(R.id.btnSaveFlight);
        btnSaveFlight.setOnClickListener(this);
        etCountry = findViewById(R.id.etCountry);
        etCity = findViewById(R.id.etCity);
        etHotel = findViewById(R.id.etHotel);
        etDescription = findViewById(R.id.etDescription);

        firebaseAuth= FirebaseAuth.getInstance();
        flight = new Flight();
        String[] countries = new String[]{
                "Australia","Canada","Denmark","Japan","Monaco","New Zealand","Swaziland","Sweden","Switzerland","United Kingdom","United States",
                "Austria","Belgium","Bulgaria","China","France",//expensive 0-15
                "Czech Republic", "Germany","Italy","Luxembourg","Spain",//average 16-20
                "Albania", "Argentina", "Armenia", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Bolivia", "Brazil", //low cost 21+
                "Burkina Faso", "Burundi", "Cambodia", "Cameroon",  "Cape Verde", "Cayman Islands",
                "Central African Republic", "Chad", "Chile",  "Christmas Island", "Cocos (Keeling) Islands", "Colombia",
                "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire",
                "Croatia (Hrvatska)", "Cuba", "Cyprus",   "Djibouti", "Dominica", "Dominican Republic",
                "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia",
                "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland",   "Gabon", "Gambia", "Georgia",  "Ghana", "Gibraltar",
                "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti",
                "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India",
                "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel",  "Jamaica",  "Jordan",
                "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait",
                "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya",
                "Liechtenstein", "Lithuania",  "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar",
                "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius",
                "Mayotte", "Mexico", "Micronesia, Federated States of", "Republic of Moldova",  "Mongolia", "Montserrat",
                "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
                "New Caledonia",  "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands",
                "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn",
                "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda",
                "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino",
                "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore",
                "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa",
                "South Georgia and the South Sandwich Islands",  "Sri Lanka", "St. Helena", "St. Pierre and Miquelon",
                "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Syrian Arab Republic",
                "Taiwan, Province of China", "Tajikistan", "United Republic of Tanzania", "Thailand", "Togo", "Tokelau", "Tonga",
                "Trinidad and Tobago", "Tunisia", "Türkiye", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
                "United Arab Emirates",   "United States Minor Outlying Islands", "Uruguay",
                "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)",
                "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countries);

        etCountry.setThreshold(1);
        etCountry.setAdapter(adapter);
        etCountry.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        etCountry.setText(parent.getItemAtPosition(position).toString());
    }
    @Override
    public void onClick(View view) {
        if (view==btnSaveFlight){
            flight.setCountry(etCountry.getText().toString());
            flight.setCity(etCity.getText().toString());
            flight.setHotel(etHotel.getText().toString());
            flight.setDescription(etDescription.getText().toString());
            flight.calculateCost();
            if(flight.getCountry().equals("")||flight.getCity().equals("")){
                Toast.makeText(this, "Please Enter Country and City", Toast.LENGTH_LONG).show();
            }
            else
            {
                checkIfFlightExists(flight);
            }
        }
        if (view==btnBooking){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.booking.com"));
            startActivity(browserIntent);
        }
    }
    private void newFlight(Flight flight) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference db = firebaseDatabase.getReference("Users/" + firebaseAuth.getUid() + "/Flights").push();
        db.setValue(flight);
    }
    private void checkIfFlightExists(Flight flight){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference tripsDB = firebaseDatabase.getReference("Users/"+firebaseAuth.getUid()+"/Flights");
        flights = new ArrayList<>();
        tripsDB.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flights.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Flight flight = new Flight();
                    flight.setCountry(data.child("country").getValue().toString());
                    flight.setCity(data.child("city").getValue().toString());
                    flights.add(flight);
                }
                boolean flag = false;
                for (Flight temp:flights) {
                    if(flight.compareTo(temp) > 0){
                        flag = true;
                    }
                }
                if (flag){
                    Toast.makeText(FlightSearchActivity.this, "A Flight to "+flight.getCountry() + ", "+flight.getCity() + " Already Exists", Toast.LENGTH_LONG).show();
                }
                else{
                    newFlight(flight);
                    Intent intent = new Intent(FlightSearchActivity.this,FlightListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}