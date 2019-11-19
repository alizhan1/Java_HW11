package space.harbour.java.hw11;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

    public ChatClient(String name, String server, int port) {
        try (Socket socket = new Socket(server, port);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            out.writeUTF(name);
            out.flush();

            while (true) {
                // Blocking call, will wait here until there is data from server
                System.out.println(in.readUTF());

                // Another blocking call, will wait until user types something
                out.writeUTF(console.readLine());
                out.flush();
            }
        } catch (UnknownHostException e) {
            System.out.println("Server " + server + " not found");
        } catch (IOException e) {
            System.out.println("Lost connection to server " + server);
        }
    }

    public static void main(String... args) {
        ChatClient client = new ChatClient(args[0], "127.0.0.1", 8008);
    }
}