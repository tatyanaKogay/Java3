package ru.gb.j_two.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class RegisterWindow extends JFrame implements ActionListener {
    JLabel loginL;
    JLabel passwordL;
    JLabel nicknameL;

    JTextField login;
    JTextField password;
    JTextField nickname;

    JButton register;

    ClientGUI gui;



    public RegisterWindow(ClientGUI gui){
        this.gui = gui;
        setTitle("Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(200,100);


        JPanel panel = new JPanel(new GridLayout(7,1));
        loginL = new JLabel("Login:");
        passwordL = new JLabel("Password:");
        nicknameL = new JLabel("Nickname:");
        login = new JTextField(20);
        password = new JTextField(20);
        nickname = new JTextField(20);
        register = new JButton("Register");
        register.addActionListener(this);



        panel.add(loginL);
        panel.add(login);
        panel.add(passwordL);
        panel.add(password);
        panel.add(nicknameL);
        panel.add(nickname);
        panel.add(register);

        getContentPane().add(panel);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (areFieldsFull()){
            gui.setRegistrationReq(true);
            gui.regis[0] = login.getText();
            gui.regis[1] = password.getText();
            gui.regis[2] = nickname.getText();
            gui.connect();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Full all fields");
        }
    }

    private boolean areFieldsFull(){
        return (login.getText()!=null && password.getText()!=null && nickname.getText()!=null);
    }


}
