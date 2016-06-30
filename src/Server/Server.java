package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server {
    static Vector ClientSockets;
    static Vector LoginNames;

    Server() throws IOException{
        ServerSocket server = new ServerSocket(4000);
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

            String LoginName = dataInputStream.readUTF();

            LoginNames.add(LoginName);
            ClientSockets.add(ClientSocket);

            start();
        }

        public void run() {
            while (true){
                try {
                    String clientMessage = dataInputStream.readUTF();
                    StringTokenizer stringTokenizer = new StringTokenizer(clientMessage);
                    String LoginName = stringTokenizer.nextToken();
                    String messageType = stringTokenizer.nextToken();
                    int logout = -1;
                    String message = "";

                    while (stringTokenizer.hasMoreTokens()){
                        message = message + " " + stringTokenizer.nextToken();
                    }

                    if (messageType.equals("LOGIN")){
                        int i;
                        for(i = 0; i < LoginNames.size(); i++){
                            Socket pSocket = (Socket) ClientSockets.elementAt(i);
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF(LoginName + " has logged in.");
                        }

                    }
                    else if (messageType.equals("LOGOUT")){
                        int i;
                        for(i = 0; i < LoginNames.size(); i++){
                            if(LoginName == LoginNames.elementAt(i)){
                                logout = i;
                            }
                            Socket pSocket = (Socket) ClientSockets.elementAt(i);
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF(LoginName + " has logged out.");
                        }
                        if(logout >= 0){
                            LoginNames.removeElementAt(logout);
                            ClientSockets.removeElementAt(logout);
                        }

                    }
                    else {
                        int i;
                        for(i = 0; i < LoginNames.size(); i++){
                            Socket pSocket = (Socket) ClientSockets.elementAt(i);
                            DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
                            pOut.writeUTF(LoginName + ": " + message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
