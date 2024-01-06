package dacd.alejandroaleman.control;

import dacd.alejandroaleman.model.Hotel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TripadvisorHotelSupplier implements HotelSupplier{

    public TripadvisorHotelSupplier() {
    }

    public List<Hotel> get(String place) {
        List<String> links = getHotelLinks(place);
        return getHotels(links, place);
    }

    private List<Hotel> getHotels(List<String> links, String place) {
        List<Hotel> hotels = new ArrayList<>();
        for (String link : links) {
            Document doc = null;
            try {
                doc = Jsoup.connect(link).get();
            } catch (IOException e) {
                System.err.println("Error while connecting to the url: " + link);
                e.printStackTrace();
            } finally {
                if (doc != null) {
                    String nameHotelSelector = "#HEADING";
                    String priceRangeSelector = "#OVERVIEW_SUBSECTION > div:nth-child(1) > div:nth-child(2)";
                    String ratingSelector = "#ABOUT_TAB > div.ui_columns.MXlSZ > div:nth-child(1) > div.grdwI.P > span";
                    Element nameHotelElement = doc.select(nameHotelSelector).first();
                    Element priceRangeElement = doc.select(priceRangeSelector).first();
                    Element ratingElement = doc.select(ratingSelector).first();

                    String nameHotel = (nameHotelElement != null) ? nameHotelElement.text() : "Not Available";
                    String priceRangeWithText = (priceRangeElement != null) ? priceRangeElement.text() : "Price not available.";
                    String priceRange = (priceRangeWithText.matches(".*\\d+ € - \\d+ €.*")) ?
                            priceRangeWithText.replaceAll(".*?(\\d+ € - \\d+ €).*", "$1") : "Not Available";
                    String rating = (ratingElement != null) ? ratingElement.text() : "Not Available";

                    hotels.add(new Hotel(nameHotel, place, priceRange, rating));
                    System.out.println(nameHotel + ", place:" + place + ", priceRange:" + priceRange + ", rating:" + rating);
                }
            }
        }
        return hotels;
    }


    public List<String> getHotelLinks(String place) {
        List<String> links = new ArrayList<>();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(place + ".txt")) {
            if (inputStream != null) {
                try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                    while (scanner.hasNextLine()) {
                        links.add(scanner.nextLine());
                    }
                }
            } else {
                System.err.println("Archive: " + place + ".txt ; not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }
}
