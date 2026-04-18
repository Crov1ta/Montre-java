import javax.swing.*;
import java.awt.*;

public class Test extends JFrame {

    CardLayout cardLayout;
    JPanel container;

    public Test() {
        cardLayout = new CardLayout();
        container  = new JPanel(cardLayout);

        // --- Page 1 ---
        JPanel page1 = new JPanel();
        JLabel label1 = new JLabel("Je suis la page 1");
        JButton btnVers2 = new JButton("Aller page 2");
        btnVers2.addActionListener(e -> cardLayout.show(container, "page2"));
        page1.add(label1);
        page1.add(btnVers2);

        // --- Page 2 ---
        JPanel page2 = new JPanel();
        JLabel label2 = new JLabel("Je suis la page 2");
        JButton btnVers1 = new JButton("Retour page 1");
        btnVers1.addActionListener(e -> cardLayout.show(container, "page1"));
        page2.add(label2);
        page2.add(btnVers1);

        // --- On ajoute les pages au container avec un nom ---
        container.add(page1, "page1");
        container.add(page2, "page2");

        add(container);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new Test();
    }
}