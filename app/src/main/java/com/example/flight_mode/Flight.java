package com.example.flight_mode;

import java.util.Random;

public class Flight extends AbstractFlight implements Comparable {
    private String country;
    private String city;
    private String hotel;
    private String description;


    public Flight(){
        super();
        country="";
        city="";
        hotel="";
        description="";
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country=country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city=city;
    }
    public String getHotel() {
        return hotel;
    }
    public void setHotel(String hotel) {
        this.hotel=hotel;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description=description;
    }


    public void calculateCost(){
        Random rand = new Random();
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
        for (int i=0;i<countries.length;i++){
            if (countries[i].equals(country)){
                if(i<=15){
                    description+=" high cost flight";
                    flightPrice = 1000+rand.nextInt(1000);
                }
                else if (i<=20){
                    description+=" average cost flight";
                    flightPrice = 500+rand.nextInt(1000);
                }
                else{
                    description+=" low cost flight";
                    flightPrice = rand.nextInt(1000);
                }
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        Flight obj = (Flight) o;
        if(country.equals(obj.getCountry()) && city.equals(obj.getCity()))
        {
            return 1;
        }
        else return 0;
    }
}
