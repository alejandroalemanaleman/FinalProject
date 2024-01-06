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

    public double getMeanPrice() {
        // Utilizar expresiones regulares para encontrar números en el formato dado
        Pattern patronNumeros = Pattern.compile("\\d+");
        if (priceRangePerNight.equals("Not Available")) return 0.0;
        Matcher matcher = patronNumeros.matcher(priceRangePerNight);

        // Encontrar los números y calcular la media
        int suma = 0;
        int contador = 0;
        while (matcher.find()) {
            int numero = Integer.parseInt(matcher.group());
            suma += numero;
            contador++;
        }

        // Calcular la media
        if (contador > 0) {
            return (double) suma / contador;
        } else {
            // Manejar el caso en el que no se encuentran números
            return 0.0;
        }
    }
}
//RATIING