import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {
    private JFrame parentFrame;

    public StartScreen(JFrame parentFrame, Runnable onStart) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // Ajouter l'image de fond
        JLabel background = new JLabel(new ImageIcon("C:/Users/kevin/Documents/TD4/img/FondEcranDemarrage.png"));
        background.setLayout(new GridBagLayout()); // Permet de placer les boutons par-dessus l'image
        add(background, BorderLayout.CENTER);

        // Création des boutons
        JButton startButton = new JButton("START");
        JButton quitButton = new JButton("QUIT");

        // Ajouter les boutons à l'écran de démarrage
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Rendre le fond transparent pour voir l'image
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        background.add(buttonPanel, new GridBagConstraints());

        // Ajouter les listeners pour les boutons
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onStart.run(); // Transition vers la carte
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.dispose(); // Ferme la fenêtre
            }
        });
    }
}
