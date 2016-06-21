package Client;

import javax.swing.*;
import java.awt.*;

//Created with IntelliJ IDEA
//Evgeny Smirnov

public class Main {

    private static JTextArea textArea;
    private  static JTextField textField;

    public static void main(String[] args) {
        messenger();
    }

    private static void messenger() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("MyMessenger 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();

        textArea = new JTextArea(15, 30);
        textArea.setLineWrap(true);
        textArea.setEditable(false); //can't be edit

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
}
