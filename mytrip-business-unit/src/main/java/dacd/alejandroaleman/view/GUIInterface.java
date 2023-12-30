package dacd.alejandroaleman.view;

import dacd.alejandroaleman.model.Weather;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
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

        JLabel recommendationLabel = new JLabel("Based on the prediction of the weather for the next five days, it is considered more appropriate to travel to " + recommendation);
        recommendationLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel recommendationPanel = new JPanel();
        recommendationPanel.add(recommendationLabel);

        // Display weather predictions
        if (weatherMap.containsKey(recommendation)) {
            List<Weather> weathers = weatherMap.get(recommendation);
            JScrollPane weatherScrollPane = createWeatherScrollPane(weathers);
            recommendationPanel.add(weatherScrollPane);
        }

        ImageIcon imageIcon = new ImageIcon(getImageURL(recommendation));
        JLabel imageLabel = new JLabel(imageIcon);

        // Create a new panel to hold the recommendation and the image
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(recommendationPanel, BorderLayout.CENTER);
        mainPanel.add(imageLabel, BorderLayout.SOUTH);

        // Update the frame content
        frame.getContentPane().removeAll(); // Clear existing components
        frame.getContentPane().add(mainPanel);

        frame.revalidate();
        frame.repaint();
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
        String imagePath = "/" + recommendation + "_image.jpg";
        return getClass().getResource(imagePath);
    }

    public static void main(String[] args) {
        new GUIInterface().execute();
    }
}
