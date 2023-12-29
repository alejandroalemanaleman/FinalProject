package dacd.alejandroaleman.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIInterface {
    private JFrame frame;
    private JComboBox<String> dataList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIInterface guiInterface = new GUIInterface();
            guiInterface.showWaitMessage();
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

        // Crear una lista desplegable (JComboBox)
        dataList = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        dataList.setFont(new Font("Arial", Font.PLAIN, 14));

        // Botón "Continue"
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDataList();
            }
        });

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(dataList);
        mainPanel.add(continueButton);

        frame.getContentPane().add(mainPanel);

        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        frame.setVisible(true);
    }

    private void showDataList() {
        // Lógica para manejar la selección de datos
        String selectedData = (String) dataList.getSelectedItem();
        JOptionPane.showMessageDialog(null, "Selected Data: " + selectedData, "Selection Result",
                JOptionPane.INFORMATION_MESSAGE);

        // Puedes agregar más lógica según la selección del usuario aquí

        // Cierra la aplicación después de mostrar el resultado
        System.exit(0);
    }

    private void showRecommendation() {

    }
    private void showChoosePlace() {

    }
    private void showHotelsAvailable() {

    }

}
