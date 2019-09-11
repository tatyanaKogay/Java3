package ru.geekbrains.java_two.lesson_e.online.chat;

import ru.geekbrains.java_two.lesson_e.online.chat.network.ServerSocketThread;

public class ChatServer {

    ServerSocketThread server;

    void start(int port) {
        if (server != null && server.isAlive())
            System.out.println("Server is already running");
        else
            server = new ServerSocketThread("Chat server", port);
    }

    void stop() {
        if (server == null || !server.isAlive())
            System.out.println("Server not runing");
        else
            server.interrupt();
    }
}
