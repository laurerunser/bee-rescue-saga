package view.gui;

import main.Main;
import view.WelcomeView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GuiWelcomeView extends JFrame implements WelcomeView {
    private JPanel panel;

    // TODO : make it pretty

    @Override
    public void welcome() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(700, 700));
        this.setTitle("Bee Rescue Saga");
        welcomeAnimation();
    }

    private void welcomeAnimation() {
        //TODO : welcome animation : make a bee go through the screen
    }

    @Override
    public void askName(ArrayList<String> savedNames) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(new JLabel("If you wish to resume a saved game, please click on your name on the list below : "));

        for (String s : savedNames) {
            JButton b = new JButton(s);
            b.addActionListener(actionEvent -> {
                JButton b1 = (JButton) actionEvent.getSource();
                System.out.println(b1.getText());
                Main.startGame(b1.getText());
            });
            panel.add(b);
        }

        panel.add(new JLabel("Otherwise, please enter your name below : "));
        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(50, 10));
        panel.add(textField);
        JButton go = new JButton("go");
        go.addActionListener(actionEvent -> {
            String name = textField.getText();
            Main.startGame(name);
        });

        panel.add(go);
        panel.add(new JLabel("Your name must have less than 20 characters"));
        panel.add(new JLabel("Please make sure to choose a name that is not in the above list," +
                                     " or you will erase the saved game associated with it"));

        this.setContentPane(panel);
        this.setVisible(true);
    }

}
