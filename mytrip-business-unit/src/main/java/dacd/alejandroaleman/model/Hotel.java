package dacd.alejandroaleman.model;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hotel {
    private final String name;
    private final String place;
    private final String pricePerNight;
    private final String rating;


    public Hotel(String name, String place, String pricePerNight, String rating) {
        this.name = name;
        this.place = place;
        this.pricePerNight = pricePerNight;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Hotel: " + name +
                ", PricePerNight: " + pricePerNight
                + ", Rating: " + rating;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getPricePerNight() {
        return pricePerNight;
    }

    public String getRating() {
        return rating;
    }

    public double getMeanPrice() {
        // Utilizar expresiones regulares para encontrar números en el formato dado
        Pattern patronNumeros = Pattern.compile("\\d+");
        if (pricePerNight.equals("Not Available")) return Double.parseDouble(null);
        Matcher matcher = patronNumeros.matcher(pricePerNight);

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