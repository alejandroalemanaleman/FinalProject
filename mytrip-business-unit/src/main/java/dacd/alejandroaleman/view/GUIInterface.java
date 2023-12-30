package dacd.alejandroaleman.view;

import dacd.alejandroaleman.model.Hotel;
import dacd.alejandroaleman.model.Weather;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIInterface {

    private JFrame frame;
    private JComboBox<String> dataList;

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

        dataList = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        dataList.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> showRecommendation());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(dataList);
        mainPanel.add(continueButton);

        frame.getContentPane().add(mainPanel);

        // Set the frame to be fullscreen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);  // Removes window decorations (title bar, borders)

        frame.setVisible(true);
    }

    private void showRecommendation() {
        Map<String, List<Weather>> weatherMap = getWeatherMap();
        String recommendation = new PlaceRecommendationCalculator(weatherMap).calculateRecommendationScore();

        JLabel recommendationMessage = new JLabel("Based on the prediction of the weather for the next five days, it is considered more appropriate to travel to");
        recommendationMessage.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel recommendationPlace = new JLabel(recommendation);
        recommendationPlace.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel recommendationPanel = new JPanel();
        recommendationPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        recommendationPanel.add(recommendationMessage, gbc);

        gbc.gridy = 1;
        recommendationPanel.add(recommendationPlace, gbc);

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

        // Button to show hotel recommendations
        JButton hotelButton = new JButton("Show Hotel Recommendations");
        hotelButton.addActionListener(e -> showHotelRecommendations(recommendation));
        gbc.gridy = 3;
        mainPanel.add(hotelButton, gbc);

        // Update the frame content
        frame.getContentPane().removeAll(); // Clear existing components
        frame.getContentPane().add(mainPanel);

        frame.revalidate();
        frame.repaint();
    }

    private void showHotelRecommendations(String recommendation) {
        Map<String, List<Hotel>> hotelMap = getHotelMap(); // Reemplazar Hotel con tu clase real para la información del hotel

        JLabel recommendationMessage = new JLabel("Based on the analysis of available hotels, we recommend staying at");
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

        if (hotelMap.containsKey(recommendation)) {
            List<Hotel> hotels = hotelMap.get(recommendation);
            JScrollPane hotelScrollPane = createHotelScrollPane(hotels);
            hotelPanel.add(hotelScrollPane);
        }

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

        // Update the frame content
        frame.getContentPane().removeAll(); // Clear existing components
        frame.getContentPane().add(mainPanel);

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

