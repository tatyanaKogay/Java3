package ru.gb.j_two.chat.server.core;

import ru.gb.j_two.chat.common.Library;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class Censor {

    private ChatServer chatServer;
    private File file = new File("censoredList.txt");
    private HashSet<String> list = new HashSet<>();
    private HashMap<String, Integer> map = new HashMap<>();

    public Censor(ChatServer _cs){
        chatServer = _cs;
        loadFile(file);
    }

    public void loadFile(File file){
        String line;
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)){
            while ((line = bufferedReader.readLine())!=null){
                list.add(line.split(" ")[0]);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public boolean isUnmentionable(String msg, String nickname){
        String[] arr0 = msg.split(Library.DELIMITER);
        String[] arr = arr0[arr0.length-1].split(" ");
        for (int i=0; i<arr.length; i++){
            if (list.contains(arr[i])){
                int count = 0;
                if (map.containsKey(nickname))count = map.get(nickname);
                map.put(nickname, ++count);
                chatServer.putLog(map.get(nickname)+" warning from "+nickname);
                return true;
            }
        }
        return false;
    }

    public boolean isBanned(String client){
        if (map.containsKey(client))return map.get(client)>= 3;
        return false;
    }

    public HashMap getMap(){
        return map;
    }


}
