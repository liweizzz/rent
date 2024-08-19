package study.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class NIOMock {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        List<Socket> sockets = new ArrayList<>();

        while (true){
            Socket socket = serverSocket.accept();
            sockets.add(socket);
            for (int i = 0; i < sockets.size(); i++) {
                if(socket.getInputStream().available() > 0){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message = reader.readLine();
                    if (message != null) {
                        System.out.println("Received: " + message);
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                        writer.println("Echo: " + message);
                    }
                }
            }
        }
    }
}
