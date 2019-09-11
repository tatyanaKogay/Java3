package ru.gb.j_two.chat.server.core;

import ru.gb.j_two.chat.common.Library;
import ru.gb.j_two.network.SocketThread;
import ru.gb.j_two.network.SocketThreadListener;

import java.net.Socket;

public class ClientThread extends SocketThread {

    private String nickname;
    private boolean isReconnected;
    private boolean isAuthorized;

    public ClientThread(SocketThreadListener listener, String name, Socket socket) {
        super(listener, name, socket);
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    void authAccept(String nickname) {
        isAuthorized = true;
        this.nickname = nickname;
        sendMessage(Library.getAuthAccept(nickname));
    }

    void authFail() {
        sendMessage(Library.getAuthDenied());
        close();
    }

    void msgFormatError(String msg) {
        sendMessage(Library.getMsgFormatError(msg));
        close();
    }

    void reconnect() {
        isReconnected = true;
        close();
    }

    public boolean isReconnected() {
        return isReconnected;
    }
}
