package Client;

import javax.swing.*;
import java.awt.*;

//Created with IntelliJ IDEA
//Evgeny Smirnov

public class Client extends JFrame implements Runnable {
    JTextArea textArea;
    JTextField textField;

    Thread thread;

    String LoginName;

    Client(String login) { //constructor
        super(login);
        LoginName = login;

        textArea = new JTextArea(15, 30);
        textArea.setLineWrap(true);
        textArea.setEditable(false); //can't be edit

        thread = new Thread(this);
        thread.start();
        setup();
    }

    private void setup() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("MyMessenger 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();

        JScrollPane scroll_panel = new JScrollPane(textArea);
        scroll_panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll_panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        textField = new JTextField(20);
        JButton send = new JButton("Send");
        JButton update = new JButton("Update");

        panel.add(scroll_panel);
        panel.add(textField);
        panel.add(send);
        panel.add(update);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(400,340);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void run() {
    }

    public static void main(String[] args){
        Client client = new Client("User");
    }
}
