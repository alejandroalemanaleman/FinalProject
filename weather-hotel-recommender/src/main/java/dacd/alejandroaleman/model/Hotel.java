package dacd.alejandroaleman.model;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hotel {
    private final String name;
    private final String place;
    private final String priceRangePerNight;
    private final String rating;


    public Hotel(String name, String place, String priceRangePerNight, String rating) {
        this.name = name;
        this.place = place;
        this.priceRangePerNight = priceRangePerNight;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Hotel: " + name +
                ", PricePerNight: " + priceRangePerNight
                + ", Rating: " + rating;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String priceRangePerNight() {
        return priceRangePerNight;
    }

    public String getRating() {
        return rating;
    }

    public String getPriceRangePerNight() {
        return priceRangePerNight;
    }

    public double getMeanPrice() {
        Pattern patronNumeros = Pattern.compile("\\d+");
        if (priceRangePerNight.equals("Not Available")) return 0.0;
        Matcher matcher = patronNumeros.matcher(priceRangePerNight);

        int suma = 0;
        int contador = 0;
        while (matcher.find()) {
            int numero = Integer.parseInt(matcher.group());
            suma += numero;
            contador++;
        }

        if (contador > 0) {
            return (double) suma / contador;
        } else {
            return 0.0;
        }
    }
}
