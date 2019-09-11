package ru.gb.j_two.chat.client;

import ru.gb.j_two.chat.common.Library;
import ru.gb.j_two.network.SocketThread;
import ru.gb.j_two.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler, SocketThreadListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 300;
    private static final String TITLE = "Chat Client";

    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss: ");

    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(3, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Alwayson top");
    private final JTextField tfLogin = new JTextField("ivan");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");
    private final JButton btnRegister = new JButton("Register");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final JButton btnChangeNick = new JButton("Change Nickname");

    private final JList<String> userList = new JList<>();
    private final String[] EMPTY = new String[0];
    private boolean shownIoErrors = false;
    private SocketThread socketThread;
    private boolean isRegistrationReq = false;
    private RegisterWindow window;
    private ChangeNickWindow nickWindow;
    public String newNick;
    public String[] regis = new String[3];
    private boolean isConnected = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    private ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);

        log.setEditable(false);
        log.setLineWrap(true);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUsers = new JScrollPane(userList);
        scrollUsers.setPreferredSize(new Dimension(100, 0));
        cbAlwaysOnTop.addActionListener(this);
        btnLogin.addActionListener(this);
        btnSend.addActionListener(this);
        tfMessage.addActionListener(this);
        btnDisconnect.addActionListener(this);
        btnRegister.addActionListener(this);
        btnChangeNick.addActionListener(this);
        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelTop.add(btnRegister);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        panelBottom.add(btnChangeNick, BorderLayout.SOUTH);
        panelBottom.setVisible(false);
        add(panelTop, BorderLayout.NORTH);
        add(scrollLog, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
        add(scrollUsers, BorderLayout.EAST);
        setVisible(true);
    }

    public void setRegistrationReq(boolean registrationReq) {
        isRegistrationReq = registrationReq;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        showException(e);
        System.exit(1);
    }

    private void showException(Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length == 0)
            msg = "Empty Stacktrace";
        else {
            msg = e.getClass().getCanonicalName() + ": " + e.getMessage() +
                    "\n\t at " + ste[0];
        }
        JOptionPane.showMessageDialog(null, msg, "Exception", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnLogin || src == tfIPAddress || src == tfLogin || src == tfPassword || src == tfPort) {
           if (!isConnected) {
               connect();
           }
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else if (src == btnDisconnect) {
            isRegistrationReq = false;
            isConnected = false;
            socketThread.close();
        } else if(src == btnRegister){
            window = new RegisterWindow(this);
        } else if(src == btnChangeNick){
            nickWindow = new ChangeNickWindow(this);
            socketThread.sendMessage(Library.getChangeNickReq(newNick));
            System.out.println("window disposed");
        } else {
            throw new RuntimeException("Unknown source: " + src);
        }
    }

    void connect() {
        Socket socket = null;
        try {
            socket = new Socket(tfIPAddress.getText(), Integer.parseInt(tfPort.getText()));
        } catch (IOException e) {
            log.append("Exception: " + e.getMessage());
        }
        socketThread = new SocketThread(this, "Client thread", socket);
        isConnected = true;
    }

//    public SocketThread getSocketThread(){
//        return socketThread;
//    }

    void sendMessage() {
        String msg = tfMessage.getText();
        String username = tfLogin.getText();
        if ("".equals(msg)) return;
        tfMessage.setText(null);
        tfMessage.requestFocusInWindow();
        socketThread.sendMessage(Library.getClientBcast(msg));
        wrtMsgToLogFile(msg, username);
    }

    private void wrtMsgToLogFile(String msg, String username) {
        String fileName = "history_"+tfLogin.getText()+".txt";
        try (FileWriter out = new FileWriter(fileName, true)) {
            out.write(username + ": " + msg + "\n");
            out.flush();
        } catch (IOException e) {
            if (!shownIoErrors) {
                shownIoErrors = true;
                JOptionPane.showMessageDialog(this, "File write error", "Exception", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void putLog(String msg) {
        if ("".equals(msg)) return;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    /**
     * Socket Thread Events
     * */

    @Override
    public void onSocketThreadStart(SocketThread thread, Socket socket) {
        putLog("Connection Established");
    }

    @Override
    public void onSocketThreadStop(SocketThread thread) {
        putLog("Connection lost");
        panelBottom.setVisible(false);
        panelTop.setVisible(true);
        setTitle(TITLE);
        userList.setListData(EMPTY);
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        handleMessage(msg);
    }

    @Override
    public void onSocketThreadReady(SocketThread thread, Socket socket) {
        String login;
        String password;
        panelBottom.setVisible(true);
        panelTop.setVisible(false);
        loadHistory();
        if (isRegistrationReq){
            login = regis[0];
            password = regis[1];
           // thread.sendMessage(Library.getAuthRequest(login, password));
            thread.sendMessage(Library.getRegistrationRequest(regis[0], regis[1], regis[2]));
        }else {
            login = tfLogin.getText();
            password = new String(tfPassword.getPassword());
            thread.sendMessage(Library.getAuthRequest(login, password));
        }


    }

    public void loadHistory(){
        String fileName = "history_"+tfLogin.getText()+".txt";
        File file = new File(fileName);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), "UTF-8"))){
            //File file = new File(fileName);
            //RandomAccessFile raf = new RandomAccessFile(file , "r");
            int length;
            if ((length = fileLength(file))>=100) {
                for (int j=0;j<length-100; j++) br.readLine();
                for (int i = 0; i < 100; i++) putLog(br.readLine());
            }else {
                String line;
                while((line = br.readLine())!=null) putLog(line);
            }
        } catch (FileNotFoundException e){
            System.out.println("No history yet");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public int fileLength(File file){
        int count = 0;
        try(BufferedReader bf = new BufferedReader(new FileReader(file))){
            while (bf.readLine()!=null) count++;
        } catch (IOException e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void onSocketThreadException(SocketThread thread, Exception e) {
        // исключение, например, будет возникать, когда мы будем отключать клиента от чата
        // в этом случае, исключение можно считать штатным, и просто логгировать
        // Исключение создаётся поскольку внутри SocketThread мы находимся в блокирующем
        // методе readUTF() и выходим из него только по факту отключения сокета,
        // то есть исключения
        isConnected = false;
        showException(e);
    }

    private void handleMessage(String value) {
        String[] arr = value.split(Library.DELIMITER);
        String msgType = arr[0];
        switch (msgType) {
            case Library.AUTH_ACCEPT:
                setTitle(TITLE + " logged in as: " + arr[1]);
                break;
            case Library.AUTH_DENIED:
                putLog(value);
                break;
            case Library.MSG_FORMAT_ERROR:
                putLog(value);
                socketThread.close();
                break;
            case Library.TYPE_BROADCAST:
                putLog(dateFormat.format(Long.parseLong(arr[1])) +
                        arr[2] + ": " + arr[3]);
                break;
            case Library.USER_LIST:
                String users = value.substring(Library.USER_LIST.length() + Library.DELIMITER.length());
                String[] userArray = users.split(Library.DELIMITER);
                Arrays.sort(userArray);
                userList.setListData(userArray);
                break;
            default:
                throw new RuntimeException("Unknown message type: " + value);
        }
        //        wrtMsgToLogFile(msg, username);

    }
}
