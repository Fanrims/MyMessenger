package Client;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//Created with IntelliJ IDEA
//Evgeny Smirnov

public class Client extends JFrame implements Runnable {

    Socket socket;
    JTextArea textArea;
    JTextField textField;

    Thread thread;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String LoginName;

    Client(String login) throws IOException { //constructor
        super(login);
        LoginName = login;

        textArea = new JTextArea(15, 30);
        textArea.setLineWrap(true);
        textArea.setEditable(false); //can't be edit

        socket = new Socket("localhost", 4000);

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        dataOutputStream.writeUTF(LoginName);
        dataOutputStream.writeUTF(LoginName + " " + "LOGIN");

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
        while (true){
            try {
                textArea.append("\n" + dataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("User2");
    }
}
