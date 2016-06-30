package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*Created with IntelliJ IDEA
  Evgeny Smirnov*/

public class Client extends JFrame implements Runnable {

    Socket socket;
    JTextArea textArea;
    JTextField textField;
    JButton send;
    JButton logout;

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

        textField = new JTextField(20);
        send = new JButton("Send");
        logout = new JButton("Logout");

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataOutputStream.writeUTF(LoginName + " " + "DATA " + textField.getText().toString());
                    textField.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataOutputStream.writeUTF(LoginName + " " + "LOGOUT");
                    System.exit(1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

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
        //interface
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("MyMessenger 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();

        JScrollPane scroll_panel = new JScrollPane(textArea);
        scroll_panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll_panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scroll_panel);
        panel.add(textField);
        panel.add(send);
        panel.add(logout);

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
        Client client = new Client("User");
    }
}
