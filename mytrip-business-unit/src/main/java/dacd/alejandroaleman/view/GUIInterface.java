package dacd.alejandroaleman.view;

import dacd.alejandroaleman.model.Hotel;
import dacd.alejandroaleman.model.Weather;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GUIInterface {

    private JFrame frame;
    private JComboBox<String> islandComboBox;

    public void execute() {
        SwingUtilities.invokeLater(() -> {
            GUIInterface guiInterface = new GUIInterface();
            guiInterface.showWaitMessage();
            guiInterface.showRecommendation(); // Call showRecommendation after showing wait message
        });
    }

    private void showWaitMessage() {
        JOptionPane.showMessageDialog(null, "Wait at least one minute until data is loaded.",
                "Information", JOptionPane.INFORMATION_MESSAGE);

        initialize();
    }

    private void initialize() {
        frame = new JFrame("Data Selection GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Agrega algunos componentes al mainPanel, por ejemplo, un JLabel
        JLabel label = new JLabel("Hello, this is your application!");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        mainPanel.add(label);

        // También puedes agregar otros componentes según tus necesidades

        frame.getContentPane().add(mainPanel);

        // Set the frame to be fullscreen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);  // Removes window decorations (title bar, borders)

        frame.setVisible(true);
    }

    private void showRecommendation() {
        Map<String, List<Weather>> weatherMap = getWeatherMap();
        String recommendation = new PlaceRecommendationCalculator(weatherMap).calculateRecommendationScore();

        System.out.println("Recommendation: " + recommendation);

        JLabel recommendationMessage = new JLabel("Based on the prediction of the weather for the next five days, it is considered more appropriate to travel to");
        recommendationMessage.setFont(new Font("Arial", Font.PLAIN, 14));

        // Show the dropdown list before proceeding
        List<String> islands = Arrays.asList("La Graciosa", "Lanzarote", "Fuerteventura", "Gran Canaria", "Tenerife", "La Gomera", "La Palma", "El Hierro");
        islandComboBox = new JComboBox<>(islands.toArray(new String[0]));
        islandComboBox.setSelectedIndex(0); // Select the first island by default

        // Add the dropdown list to the panel
        JPanel recommendationPanel = new JPanel();
        recommendationPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        recommendationPanel.add(recommendationMessage, gbc);

        gbc.gridy = 1;
        JLabel recommendationPlace = new JLabel(recommendation);
        recommendationPlace.setFont(new Font("Arial", Font.BOLD, 28));
        recommendationPanel.add(recommendationPlace, gbc);

        gbc.gridy = 2;
        recommendationPanel.add(islandComboBox, gbc);

        // Button to view weather for the selected island
        JButton otherWeatherButton = new JButton("View Weather for Other Islands");
        otherWeatherButton.addActionListener(e -> showWeatherPredictions((String) islandComboBox.getSelectedItem()));
        gbc.gridy = 3;
        recommendationPanel.add(otherWeatherButton, gbc);

        // Button to show hotel recommendations for the selected island
        JButton hotelButton = new JButton("Show Hotel Recommendations");
        hotelButton.addActionListener(e -> showHotelRecommendations(recommendation));
        gbc.gridy = 4;
        recommendationPanel.add(hotelButton, gbc);

        // Panel to display weather information below the recommendation
        JPanel weatherPanel = new JPanel();
        if (weatherMap.containsKey(recommendation)) {
            List<Weather> weathers = weatherMap.get(recommendation);
            JScrollPane weatherScrollPane = createWeatherScrollPane(weathers);
            weatherPanel.add(weatherScrollPane);
        }

        ImageIcon imageIcon = new ImageIcon(getImageURL(recommendation));
        JLabel imageLabel = new JLabel(imageIcon);

        // Create a new panel with GridBagLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Recommendation panel in the center
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        mainPanel.add(recommendationPanel, gbc);

        // Weather panel below the recommendation
        gbc.gridy = 1;
        mainPanel.add(weatherPanel, gbc);

        // Image label at the bottom
        gbc.gridy = 2;
        mainPanel.add(imageLabel, gbc);

        // Update the frame content
        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void showWeatherPredictions(String selectedIsland) {
        Map<String, List<Weather>> weatherMap = getWeatherMap();

        JLabel otherWeatherMessage = new JLabel("Weather Predictions for " + selectedIsland + ":");
        otherWeatherMessage.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel otherWeatherPanel = new JPanel();
        otherWeatherPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        otherWeatherPanel.add(otherWeatherMessage, gbc);

        gbc.gridy = 1;
        if (weatherMap.containsKey(selectedIsland)) {
            List<Weather> weathers = weatherMap.get(selectedIsland);
            JScrollPane weatherScrollPane = createWeatherScrollPane(weathers);
            otherWeatherPanel.add(weatherScrollPane, gbc);
        }

        // Botón para volver a las recomendaciones originales
        JButton backButton = new JButton("Back to Original Recommendations");
        backButton.addActionListener(e -> showRecommendation());
        gbc.gridy++;
        otherWeatherPanel.add(backButton, gbc);

        // Crear un nuevo panel con GridBagLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Otro panel de clima en el centro
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Añadir un poco de relleno
        mainPanel.add(otherWeatherPanel, gbc);

        // Actualizar el contenido del marco
        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.repaint();
    } // Método para mostrar predicciones meteorológicas para otras islas


    // ...

    private void showHotelRecommendations(String recommendation) {
        Map<String, List<Hotel>> hotelMap = getHotelMap();

        JLabel recommendationMessage = new JLabel("Based on the analysis, these are the hotels we recommend while staying at");
        recommendationMessage.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel recommendationHotel = new JLabel(recommendation);
        recommendationHotel.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel recommendationPanel = new JPanel();
        recommendationPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        recommendationPanel.add(recommendationMessage, gbc);

        gbc.gridy = 1;
        recommendationPanel.add(recommendationHotel, gbc);

        // Display hotel information (similar structure to weather predictions)
        JPanel hotelPanel = new JPanel();


        List<Hotel> hotels = hotelMap.get(recommendation);
        JScrollPane hotelScrollPane = createHotelScrollPane(hotels);
        hotelPanel.add(hotelScrollPane);


        // Components for reservation
        JPanel reservationPanel = new JPanel();
        JLabel selectHotelLabel = new JLabel("Select Hotel:");
        JComboBox<String> hotelComboBox = new JComboBox<>(hotels.stream()
                .map(Hotel::getName)
                .toArray(String[]::new));

        JLabel numberOfPeopleLabel = new JLabel("Select number of guests:");
        JComboBox<Integer> numberOfPeopleComboBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});

        JLabel numberOfNightsLabel = new JLabel("Select number of nights:");
        JComboBox<Integer> numberOfNightsField = new JComboBox<>(new Integer[]{1, 2, 3, 4});

        JButton reserveButton = new JButton("Reserve");
        reserveButton.addActionListener(e -> {
            OptionalDouble priceOptional = hotels.stream()
                    .filter(hotel -> hotel.getName().equals(hotelComboBox.getSelectedItem()))
                    .mapToDouble(Hotel::getMeanPrice)
                    .findFirst();

            // Verifica si el precio está disponible antes de intentar obtener el valor
            if (priceOptional.isPresent()) {
                double price = priceOptional.getAsDouble();

                makeReservation(hotelComboBox.getSelectedItem(), recommendation,
                        (Integer) numberOfPeopleComboBox.getSelectedItem(),
                        (Integer) numberOfNightsField.getSelectedItem(),
                        price);
            } else {
                // Manejo si el precio no está disponible
                System.out.println("Price not available for the selected hotel.");
            }
        });


        reservationPanel.add(selectHotelLabel);
        reservationPanel.add(hotelComboBox);
        reservationPanel.add(numberOfPeopleLabel);
        reservationPanel.add(numberOfPeopleComboBox);
        reservationPanel.add(numberOfNightsLabel);
        reservationPanel.add(numberOfNightsField);
        reservationPanel.add(reserveButton);

        // Back button to switch back to weather recommendations
        JButton backButton = new JButton("Back to Weather Recommendations");
        backButton.addActionListener(e -> showRecommendation());
        gbc.gridy = 3;
        recommendationPanel.add(backButton, gbc);

        // Create a new panel with GridBagLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Recommendation panel in the center
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        mainPanel.add(recommendationPanel, gbc);

        // Hotel panel below the recommendation
        gbc.gridy = 1;
        mainPanel.add(hotelPanel, gbc);

        // Reservation panel below the hotel panel
        gbc.gridy = 2;
        mainPanel.add(reservationPanel, gbc);

        // Update the frame content
        frame.getContentPane().removeAll(); // Clear existing components
        frame.getContentPane().add(mainPanel);

        frame.revalidate();
        frame.repaint();
    }

    private void makeReservation(Object selectedHotel, String place, int numberOfPeople, int numberOfNights, double meanPrice) {
        // Implement reservation logic here
        // You can use the selectedHotel, numberOfPeople, and numberOfNights values
        // to process the reservation, show confirmation, or handle any other actions.
        // For now, let's just print the values.
        System.out.println("Reservation Details:");
        System.out.println("Hotel: " + selectedHotel);
        System.out.println("Number of People: " + numberOfPeople);
        System.out.println("Number of Nights: " + numberOfNights);

        double totalPrice = meanPrice * numberOfNights;
        System.out.println("Approximate total: " + totalPrice + "€");

        // Create a label to display the reservation details
        JLabel reservationDetailsLabel = new JLabel("<html><b>Reservation Details:</b><br>"
                + "Hotel: " + selectedHotel + "<br>"
                + "Place: " + place + "<br>"
                + "Number of People: " + numberOfPeople + "<br>"
                + "Number of Nights: " + numberOfNights + "<br>"
                + "Approximate total: " + totalPrice + "€</html>");

        // Components for reservation
        JPanel reservationPanel = new JPanel();
        reservationPanel.add(reservationDetailsLabel);

        // Button to go back to hotel recommendations
        JButton backButton = new JButton("Back to Hotel Recommendations");
        backButton.addActionListener(e -> showHotelRecommendations(place));

        reservationPanel.add(backButton);

        // Update the frame content
        frame.getContentPane().removeAll(); // Clear existing components
        frame.getContentPane().add(reservationPanel);

        frame.revalidate();
        frame.repaint();
    }



    private Map<String, List<Hotel>> getHotelMap() {
        DatamartProvider datamartProvider = new DatamartProvider("/Users/alejandroalemanaleman/Downloads/");
        List<String> places = List.of("La Graciosa", "Lanzarote", "Fuerteventura", "Gran Canaria", "Tenerife", "La Gomera", "La Palma", "El Hierro");
        return datamartProvider.getHotelsFromPlaces(places);
    }

    private JScrollPane createHotelScrollPane(List<Hotel> hotels) {
        JPanel hotelPanel = new JPanel();
        hotelPanel.setLayout(new GridLayout(0, 1, 0, 10)); // Increase the last parameter (10 in this example) to add more space

        for (Hotel hotel : hotels) {
            JLabel hotelLabel = new JLabel(hotel.toString());
            hotelPanel.add(hotelLabel);
        }

        JScrollPane scrollPane = new JScrollPane(hotelPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollPane;
    }





    private Map<String, List<Weather>> getWeatherMap() {
        DatamartProvider datamartProvider = new DatamartProvider("/Users/alejandroalemanaleman/Downloads/");
        List<String> places = List.of("La Graciosa", "Lanzarote", "Fuerteventura", "Gran Canaria", "Tenerife", "La Gomera", "La Palma", "El Hierro");
        return datamartProvider.getPredictionFromPlaces(places);
    }

    private JScrollPane createWeatherScrollPane(List<Weather> weathers) {
        JPanel weatherPanel = new JPanel();
        weatherPanel.setLayout(new GridLayout(weathers.size(), 1));

        for (Weather weather : weathers) {
            JLabel weatherLabel = new JLabel(weather.toString());
            weatherPanel.add(weatherLabel);
        }
        JScrollPane scrollPane = new JScrollPane(weatherPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        return scrollPane;
    }

    private URL getImageURL(String recommendation) {
        String imagePath = "/" + recommendation.replaceAll(" ", "_") + "_image.jpg";
        return getClass().getResource(imagePath);
    }

    public static void main(String[] args) {
        new GUIInterface().execute();
    }
}

