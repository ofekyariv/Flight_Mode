package com.example.flight_mode;


public abstract class AbstractFlight implements Comparable {
    int flightPrice;
    String key;

    public AbstractFlight(){
        flightPrice=0;
        key="";
    }

    public int getFlightPrice() {
        return flightPrice;
    }
    public void setFlightPrice(int flightPrice) {
        this.flightPrice=flightPrice;
    }
    public void setKey(String key) {
        this.key=key;
    }
    public String getKey() {
        return key;
    }

    @Override
    public abstract int compareTo(Object o);
}
