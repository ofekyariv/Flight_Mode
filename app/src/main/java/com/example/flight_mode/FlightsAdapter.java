package com.example.flight_mode;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FlightsAdapter extends ArrayAdapter<Flight> {

    Context context;
    List<Flight> flights;
    public FlightsAdapter(@NonNull Context context, List<Flight> flights) {
        super(context,R.layout.flight, flights);
        this.context=context;
        this.flights = flights;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        View view= layoutInflater.inflate(R.layout.flight,parent,false);

        TextView txtCountry= (TextView)view.findViewById(R.id.txtCountry);
        TextView txtCity= (TextView)view.findViewById(R.id.txtCity);
        TextView txtHotel= (TextView)view.findViewById(R.id.txtHotel);
        TextView txtCost= (TextView)view.findViewById(R.id.txtCost);
        TextView txtDescription= (TextView)view.findViewById(R.id.txtDescription);

        Flight flight= flights.get(position);

        txtCountry.setText(flight.getCountry());
        txtCity.setText(flight.getCity());
        txtHotel.setText(flight.getHotel());
        txtCost.setText(flight.getFlightPrice() +"$");
        txtCost.setTextColor(Color.GREEN);
        txtDescription.setText(flight.getDescription());

        return view;
    }
}
