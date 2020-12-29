package view.gui;

import view.WelcomeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GuiWelcomeView implements WelcomeView, ActionListener {
    private final JFrame frame = new JFrame();
    private JPanel panel;
    private boolean nameIsChosen = false;
    private String name;

    public boolean nameChosen() {
        return nameIsChosen;
    }

    public String getName() { return name; }

    @Override
    public void welcome() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        welcomeAnimation();
    }

    private void welcomeAnimation() {
        //TODO : welcome animation : make a bee go through the screen
    }

    @Override
    public String askName(ArrayList<String> savedNames) {
        panel = new JPanel();
        frame.setContentPane(panel);
        frame.setVisible(true);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(new JLabel("If you wish to resume a saved game, please select your name on the list below"));

        ButtonGroup group = new ButtonGroup();
        for (String s : savedNames) {
            JRadioButton b = new JRadioButton(s);
            b.addActionListener(this);
            b.setActionCommand(s); // give the action of the button the name in the savedNames list
            group.add(b);
            panel.add(b);
            panel.setVisible(true);
            b.setVisible(true);
        }

        panel.add(new JLabel("Otherwise, please enter your name below : "));
        JTextField textField = new JTextField(20);
        textField.addActionListener(this);
        panel.add(textField);
        panel.add(new JLabel("Your name must have less than 20 characters"));
        panel.add(new JLabel("Please make sure to choose a name that is not in the above list," +
                                     " or you will erase the saved game associated with it"));
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() instanceof JRadioButton) {
            // get the name on the selected button
            name = actionEvent.getActionCommand();
            System.out.println(name);
        } else {
            // get the name in the input box
            name = ((JTextField) actionEvent.getSource()).getText();
        }
        nameIsChosen = true;
    }


}
