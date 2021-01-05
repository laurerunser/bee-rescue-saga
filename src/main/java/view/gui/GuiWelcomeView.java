package view.gui;

import main.Main;
import view.WelcomeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GuiWelcomeView extends JFrame implements WelcomeView {
    private JPanel panel;

    // TODO : make it pretty

    @Override
    public void welcome() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(900, 900));
        this.setTitle("Bee Rescue Saga");
        welcomeAnimation();
    }

    private void welcomeAnimation() {
        //TODO : welcome animation : make a bee go through the screen
    }

    @Override
    public void askName(ArrayList<String> savedNames) {
        panel = new JPanel();
        //panel.setBackground(new Color(237, 198, 63));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBackground(new Color(237, 198, 63));


        ImageIcon banner = new ImageIcon(getClass().getClassLoader().getResource("pictures/Banner.png"));
        panel.add(new JLabel(banner));

        ImageIcon listNames = new ImageIcon(getClass().getClassLoader().getResource("pictures/ListNames.png"));
        panel.add(new JLabel(listNames));
        for (String s : savedNames) {
            JButton b = new JButton(s);
            b.addActionListener(actionEvent -> {
                JButton b1 = (JButton) actionEvent.getSource();
                Main.startGame(b1.getText());
            });
            panel.add(b);
        }

        panel.add(Box.createRigidArea(new Dimension(900, 30)));

        ImageIcon promptName = new ImageIcon(getClass().getClassLoader().getResource("pictures/EnterName.png"));
        panel.add(new JLabel(promptName));

        JPanel panelTextField = new JPanel();
        panelTextField.setPreferredSize(new Dimension(900, 50));
        panelTextField.setBackground(new Color(237, 198, 63));
        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(50, 40));
        panelTextField.add(textField);
        panel.add(panelTextField);
        panel.add(Box.createRigidArea(new Dimension(900, 15)));

        panel.add(new JLabel("Your name must have less than 20 characters"));
        panel.add(new JLabel("Please make sure to choose a name that is not in the above list," +
                                     " or you will erase the saved game associated with it"));
        panel.add(Box.createRigidArea(new Dimension(900, 10)));

        ImageIcon letsGo = new ImageIcon(getClass().getClassLoader().getResource("pictures/LetsGo.png"));
        JButton go = new JButton(letsGo);
        go.setContentAreaFilled(false);
        go.setBorder(null);
        go.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String name = textField.getText();
                Main.startGame(name);
            }

            public void mousePressed(MouseEvent mouseEvent) { }

            public void mouseReleased(MouseEvent mouseEvent) { }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                go.setIcon(new ImageIcon(getClass().getClassLoader().getResource("pictures/LetsGoHover.png")));
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                go.setIcon(new ImageIcon(getClass().getClassLoader().getResource("pictures/LetsGo.png")));
            }
        });
        panel.add(go);

        panel.add(Box.createRigidArea(new Dimension(900, 200)));

        this.setContentPane(panel);
        this.setVisible(true);
    }

}
