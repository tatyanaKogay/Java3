package ru.gb.j_two.chat.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ru.gb.j_two.chat.common.Library;


public class ChangeNickWindow extends JFrame implements ActionListener {
    JPanel panel;
    JLabel newNick;
    JTextField field;
    ClientGUI gui;

    public ChangeNickWindow(ClientGUI gui){
        this.gui = gui;
        setTitle("Change your nickname");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(200,100);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        newNick = new JLabel("Enter new nick");
        field = new JTextField(20);
        field.addActionListener(this);
        panel.add(newNick);
        panel.add(field);

        getContentPane().add(panel);
        pack();
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (field.getText()!=null){
            gui.getSocketThread().sendMessage(Library.getChangeNickReq(field.getText()));
            dispose();
            System.out.println("disposed");
        } else {
            JOptionPane.showMessageDialog(this, "Full field");
        }
    }
}
