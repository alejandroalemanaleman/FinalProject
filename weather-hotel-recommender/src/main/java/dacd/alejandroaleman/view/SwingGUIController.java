package dacd.alejandroaleman.view;

import dacd.alejandroaleman.model.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class SwingGUIController implements GUIController {
    private JFrame frame;
    private JComboBox<String> islandComboBox;
    private DatamartProvider datamartProvider;

    public SwingGUIController(DatamartProvider datamartProvider) {
        this.datamartProvider = datamartProvider;
    }

    public void execute() {
        SwingUtilities.invokeLater(() -> {
            showWaitMessage();
            showRecommendation();
        });
    }

    private void showWaitMessage() {
        JOptionPane.showMessageDialog(null, "Continue ONLY when BOTH modules hotel-provider and prediction-provider show this message:\n " +
                        "-----[MESSAGE]: ALL DATA SENT.-----",
                "Information before continuing", JOptionPane.INFORMATION_MESSAGE);

        initialize();
    }

    private void initialize() {
        frame = new JFrame("Data Selection GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Hello, this is your application!");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        mainPanel.add(label);
        frame.getContentPane().add(mainPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    private void showRecommendation() {
        Map<String, List<Weather>> weatherMap = datamartProvider.getPrediction();
        String recommendation = new PlaceRecommendationCalculator(weatherMap).calculateRecommendationScore();

        JLabel recommendationMessage = new JLabel("Based on the prediction of the weather for the next five days, it is considered more appropriate to travel to");
        recommendationMessage.setFont(new Font("Arial", Font.PLAIN, 14));

        List<String> islands = Arrays.asList("La Graciosa", "Lanzarote", "Fuerteventura", "Gran Canaria", "Tenerife", "La Gomera", "La Palma", "El Hierro");
        islandComboBox = new JComboBox<>(islands.toArray(new String[0]));
        islandComboBox.setSelectedIndex(0);

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

        JButton otherWeatherButton = new JButton("Show me weather predictions for this island");
        otherWeatherButton.addActionListener(e -> showOtherPredictions((String) islandComboBox.getSelectedItem()));
        gbc.gridy = 3;
        recommendationPanel.add(otherWeatherButton, gbc);

        JButton hotelButton = new JButton("Show Hotel Recommendations");
        hotelButton.addActionListener(e -> showHotelRecommendations(recommendation));
        gbc.gridy = 4;
        recommendationPanel.add(hotelButton, gbc);

        JPanel weatherPanel = new JPanel();
        if (weatherMap.containsKey(recommendation)) {
            List<Weather> weathers = weatherMap.get(recommendation);
            JScrollPane weatherScrollPane = createWeatherScrollPane(weathers);
            weatherPanel.add(weatherScrollPane);
        }

        ImageIcon imageIcon = new ImageIcon(getImageURL(recommendation));
        JLabel imageLabel = new JLabel(imageIcon);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        mainPanel.add(recommendationPanel, gbc);

        gbc.gridy = 1;
        mainPanel.add(weatherPanel, gbc);

        gbc.gridy = 2;
        mainPanel.add(imageLabel, gbc);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void showOtherPredictions(String selectedPlace) {
        Map<String, List<Weather>> weatherMap = datamartProvider.getPrediction();

        JLabel otherWeatherMessage = new JLabel("Weather Predictions for");
        otherWeatherMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel selectedPlaceLabel = new JLabel(selectedPlace);
        selectedPlaceLabel.setFont(new Font("Arial", Font.BOLD, 29));

        JPanel otherWeatherPanel = new JPanel();
        otherWeatherPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        otherWeatherPanel.add(otherWeatherMessage, gbc);
        gbc.gridy++;
        otherWeatherPanel.add(selectedPlaceLabel, gbc);
        gbc.gridy++;

        if (weatherMap.containsKey(selectedPlace)) {
            List<Weather> weathers = weatherMap.get(selectedPlace);
            JScrollPane weatherScrollPane = createWeatherScrollPane(weathers);
            otherWeatherPanel.add(weatherScrollPane, gbc);
        }

        JButton backButton = new JButton("Back to Original Recommendations");
        backButton.addActionListener(e -> showRecommendation());
        gbc.gridy++;
        otherWeatherPanel.add(backButton, gbc);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JButton hotelButton = new JButton("Show Hotel Recommendations");
        hotelButton.addActionListener(e -> showHotelRecommendations(selectedPlace));
        gbc.gridy = 4;
        mainPanel.add(hotelButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Añadir un poco de relleno
        mainPanel.add(otherWeatherPanel, gbc);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }


    private void showHotelRecommendations(String recommendation) {
        Map<String, List<Hotel>> hotelMap = datamartProvider.getHotels();

        JLabel recommendationMessage = new JLabel("These are the hotels we recommend while staying at");
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
        JPanel hotelPanel = new JPanel();
        List<Hotel> hotels = hotelMap.get(recommendation);
        JScrollPane hotelScrollPane = createHotelScrollPane(hotels);
        hotelPanel.add(hotelScrollPane);

        JPanel reservationPanel = new JPanel();
        JLabel selectHotelLabel = new JLabel("Select Hotel:");
        JComboBox<String> hotelComboBox = new JComboBox<>(hotels.stream()
                .map(Hotel::getName)
                .toArray(String[]::new));

        JLabel numberOfPeopleLabel = new JLabel("Select number of rooms:");
        JComboBox<Integer> numberOfRoomsComboBox = new JComboBox<>(new Integer[]{1, 2, 3});
        JLabel numberOfNightsLabel = new JLabel("Select number of nights:");
        JComboBox<Integer> numberOfNightsField = new JComboBox<>(new Integer[]{1, 2, 3, 4});

        JButton reserveButton = new JButton("Calculate approximate total price");
        reserveButton.addActionListener(e -> {
            OptionalDouble priceOptional = hotels.stream()
                    .filter(hotel -> hotel.getName().equals(hotelComboBox.getSelectedItem()))
                    .mapToDouble(Hotel::getMeanPrice)
                    .findFirst();

            if (priceOptional.isPresent()) {
                double price = priceOptional.getAsDouble();

                makeReservation(hotelComboBox.getSelectedItem(), recommendation,
                        (Integer) numberOfRoomsComboBox.getSelectedItem(),
                        (Integer) numberOfNightsField.getSelectedItem(),
                        price);
            } else {
                System.out.println("Price not available for the selected hotel.");
            }
        });


        reservationPanel.add(selectHotelLabel);
        reservationPanel.add(hotelComboBox);
        reservationPanel.add(numberOfPeopleLabel);
        reservationPanel.add(numberOfRoomsComboBox);
        reservationPanel.add(numberOfNightsLabel);
        reservationPanel.add(numberOfNightsField);
        reservationPanel.add(reserveButton);

        JButton backButton = new JButton("Back to Weather Recommendations");
        backButton.addActionListener(e -> showRecommendation());
        gbc.gridy = 3;
        recommendationPanel.add(backButton, gbc);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(recommendationPanel, gbc);

        gbc.gridy = 1;
        mainPanel.add(hotelPanel, gbc);

        gbc.gridy = 2;
        mainPanel.add(reservationPanel, gbc);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainPanel);

        frame.revalidate();
        frame.repaint();
    }

    private void makeReservation(Object selectedHotel, String place, int numberOfRooms, int numberOfNights, double meanPrice) {
        System.out.println("Reservation Details:");
        System.out.println("Hotel: " + selectedHotel);
        System.out.println("Number of Rooms: " + numberOfRooms);
        System.out.println("Number of Nights: " + numberOfNights);

        double totalPrice = meanPrice * numberOfNights * numberOfRooms;
        System.out.println("Approximate total: " + totalPrice + "€");

        JLabel reservationDetailsLabel = new JLabel("<html><b>Reservation Details:</b><br>"
                + "Hotel: " + selectedHotel + "<br>"
                + "Place: " + place + "<br>"
                + "Number of Rooms: " + numberOfRooms + "<br>"
                + "Number of Nights: " + numberOfNights + "<br>"
                + "Approximate total: " + totalPrice + "€</html>");

        JPanel reservationPanel = new JPanel();
        reservationPanel.add(reservationDetailsLabel);

        JButton backButton = new JButton("Back to Hotel Recommendations");
        backButton.addActionListener(e -> showHotelRecommendations(place));

        reservationPanel.add(backButton);

        frame.getContentPane().removeAll(); // Clear existing components
        frame.getContentPane().add(reservationPanel);

        frame.revalidate();
        frame.repaint();
    }

    private JScrollPane createHotelScrollPane(List<Hotel> hotels) {
        JPanel hotelPanel = new JPanel();
        hotelPanel.setLayout(new GridLayout(0, 1, 0, 10));

        for (Hotel hotel : hotels) {
            JLabel hotelLabel = new JLabel(hotel.toString());
            hotelPanel.add(hotelLabel);
        }
        JScrollPane scrollPane = new JScrollPane(hotelPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
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
        new SwingGUIController(new DatamartProvider("/Users/alejandroalemanaleman/Downloads/probandooooooooaaa", List.of("La Graciosa", "Lanzarote", "Fuerteventura", "Gran Canaria", "Tenerife", "La Gomera", "La Palma", "El Hierro"))).execute();
    }
}
