package ru.gb.j_two.chat.server.core;

import ru.gb.j_two.chat.common.Library;
import ru.gb.j_two.network.ServerSocketThread;
import ru.gb.j_two.network.ServerSocketThreadListener;
import ru.gb.j_two.network.SocketThread;
import ru.gb.j_two.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    private ServerSocketThread server;
    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss: ");
    private ChatServerListener listener;
    private Vector<SocketThread> clients = new Vector<>();
    private Censor censor;
    private HashMap<ClientThread, Integer> bannedClients = new HashMap<>();
    private ExecutorService es;


    public ChatServer(ChatServerListener listener){
        this.listener = listener;
        censor = new Censor(this);
        es = Executors.newCachedThreadPool();
    }

    public void start(int port) {
        if (server != null && server.isAlive()) {
            putLog("Server is already running");
        } else {
            server = new ServerSocketThread(this, "Chat server", port, 3000);
        }
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            putLog("Server is not running");
        } else {
            server.interrupt();
        }
    }

    public void putLog(String msg) {
        msg = dateFormat.format(System.currentTimeMillis()) +
                Thread.currentThread().getName() + ": " + msg;
        listener.onChatServerMessage(msg);
    }

    /**
     * Server Socket Thread Events
     * */

    @Override
    public void onServerSocketThreadStart(ServerSocketThread thread) {
        putLog("server thread start");
        SqlClient.connect();
    }

    @Override
    public void onServerSocketCreate(ServerSocketThread thread, ServerSocket server) {
        putLog("server socket created");
    }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, Socket socket) {
        putLog("socket accepted");
        String name = "SocketThread" + socket.getInetAddress() + ":" + socket.getPort();
        es.execute(new ClientThread(this, name, socket));
    }

    @Override
    public void onServerSocketAcceptTimeout(ServerSocketThread thread, ServerSocket server) {
    }

    @Override
    public void onServerSocketThreadException(ServerSocketThread thread, Exception e) {
        putLog("server thread exception");
    }

    @Override
    public void onServerSocketThreadStop(ServerSocketThread thread) {
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).close();
        }
        SqlClient.disconnect();
    }

    /**
     * Socket Thread Events
     * */

    @Override
    public synchronized void onSocketThreadStart(SocketThread thread, Socket socket) {
        putLog("start socket thread");
    }

    @Override
    public synchronized void onSocketThreadStop(SocketThread thread) {
        ClientThread client = (ClientThread) thread;
        clients.remove(thread);
        if (client.isAuthorized() && !client.isReconnected()) {
            sendToAuthorizedClients(Library.getTypeBroadcast("Server", client.getNickname() + " disconnected"));
            sendToAuthorizedClients(Library.getUserList(getUsers()));
        }
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String msg) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()) {
            handleAuthMsg(client, msg);
        } else {
            handleNonAuthMsg(client, msg);
        }
    }

    @Override
    public synchronized void onSocketThreadReady(SocketThread thread, Socket socket) {
        bannedClients = censor.getMap();
        clients.add(thread);
        putLog("socket thread is ready");
    }

    @Override
    public synchronized void onSocketThreadException(SocketThread thread, Exception e) {
        putLog("Exception" + e.getClass().getName() + ": " + e.getMessage());
    }

    private String getUsers() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            sb.append(client.getNickname()).append(Library.DELIMITER);
        }
        return sb.toString();
    }

    private void handleAuthMsg(ClientThread thread, String msg) {
        String[] arr = msg.split(Library.DELIMITER);
        String msgType = arr[0];
        switch (msgType) {
            case Library.CLIENT_BCAST:
                censor.isUnmentionable(msg, thread.getNickname());
                sendToAuthorizedClients(Library.getTypeBroadcast(thread.getNickname(), arr[1]));
                break;
            case Library.CH_NICK_REQ:
                SqlClient.changeNick(thread.getNickname(), arr[1]);
                System.out.println("server got the message");
                break;
            default:
                thread.msgFormatError("Take your sh*t back: " + msg);
        }
    }

    private void handleNonAuthMsg(ClientThread newClient, String msg) {
        if (msg.startsWith(Library.REG_REQ)){
            String[] arr = msg.split(Library.DELIMITER);
            SqlClient.register(arr[1], arr[2], arr[3]);
            newClient.authAccept(arr[3]);
        } else {
            String[] arr = msg.split(Library.DELIMITER);
            if (arr.length != 3 || !arr[0].equals(Library.AUTH_REQUEST)) {
                newClient.msgFormatError(msg);
                return;
            }
            String login = arr[1];
            String password = arr[2];
            String nickname = SqlClient.getNickname(login, password);
            if (nickname == null) {
                putLog(String.format("Invalid login/password: login='%s', password='%s'", login, password));
                newClient.authFail();
            } else {
                ClientThread client = findClientThreadByNickname(nickname);
                newClient.authAccept(nickname);
                if(censor.isBanned(newClient.getNickname())) putLog("Warning: "+newClient.getNickname()+" connected!");
                if (client == null)
                    sendToAuthorizedClients(Library.getTypeBroadcast("Server", nickname + " connected"));
                else {
                    client.reconnect();
                    clients.remove(client);
                }
            }

            sendToAuthorizedClients(Library.getUserList(getUsers()));
        }
    }

    private void sendToAuthorizedClients(String msg) {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            client.sendMessage(msg);
        }
    }

    private synchronized ClientThread findClientThreadByNickname(String nickname) {
        for (int i = 0; i < clients.size(); i++) {
            ClientThread client = (ClientThread) clients.get(i);
            if (!client.isAuthorized()) continue;
            if (client.getNickname().equals(nickname))
                return client;
        }
        return null;
    }

}
