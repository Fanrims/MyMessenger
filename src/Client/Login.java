package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Login {
    public static void main(String[] args){
        /*** interface***/
        final JFrame login = new JFrame("Log in");
        JPanel panel = new JPanel();
        final JTextField loginName = new JTextField(20);
        JButton enter = new JButton("Log in");

        panel.add(loginName);
        panel.add(enter);
        login.setSize(300, 100);
        login.add(panel);
        login.setVisible(true);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //let's add action to the button
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Client client = new Client(loginName.getText());
                    login.setVisible(false);
                    login.dispose();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        loginName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        Client client = new Client(loginName.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    login.setVisible(false);
                    login.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
