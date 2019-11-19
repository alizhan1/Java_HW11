package space.harbour.java.hw11;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class ChatServer {
    private Set<ChatHandler> chats = ConcurrentHashMap.newKeySet();

    public ChatServer(int port) {
        try (ServerSocket s = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                ChatHandler chat = new ChatHandler(this, s.accept());
                chats.add(chat);
                chat.start();
            }
        } catch (IOException e) {
            System.out.println("Server failed on port " + port);
        }
    }

    public synchronized void broadcast(String message) {
        System.out.println("New message -> " + message);
        for (ChatHandler chat: chats) {
            chat.sendMessage(message);
        }
    }

    public void chatDisconnected(ChatHandler chat) {
        chats.remove(chat);
        broadcast("Chat member " + chat.name + " left");
    }

    public static void main(String[] args) {
        new ChatServer(8008);
    }
}