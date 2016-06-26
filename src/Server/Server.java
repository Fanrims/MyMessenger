package Server;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server {
    static Vector ClientSockets;
    static Vector LoginNames;

    Server() throws IOException{
        ServerSocket server = new ServerSocket(5000);
        ClientSockets = new Vector();
        LoginNames = new Vector();

        while (true) {
            Socket client = server.accept();
            AcceptClient acceptClient = new AcceptClient(client);
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
    }

    class AcceptClient extends Thread {
        Socket ClientSocket;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        AcceptClient(Socket client) throws IOException{
            ClientSocket = client;
            dataInputStream = new DataInputStream(ClientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(ClientSocket.getOutputStream());

            String Login = dataInputStream.readUTF();

            LoginNames.add(Login);
            ClientSockets.add(ClientSocket);

            start();
        }

        public void run() {
            while (true){
                try {
                    String clientMessage = dataInputStream.readUTF();
                    StringTokenizer stringTokenizer = new StringTokenizer(clientMessage);
                    String Login = stringTokenizer.nextToken();
                    String messageType = stringTokenizer.nextToken();
                    int i;
                    for (i = 0; i < LoginNames.size(); i++) ;
                    Socket pSocket = (Socket) ClientSockets.elementAt(i);
                    DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                    pOut.writeUTF(Login + " has logged in.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
