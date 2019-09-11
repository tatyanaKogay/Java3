package ru.gb.j_two.chat.server.core;

import java.sql.*;

public class SqlClient {

    private static Connection connection;
    private static Statement statement;

    synchronized static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:chatDb.sqlite");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static String getNickname(String login, String password) {
        String request = String.format("select nickname from users where login='%s' and password='%s'", login, password);
        try (ResultSet set = statement.executeQuery(request)) {
            if (set.next()) {
                return set.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    synchronized static void register(String login, String password, String nickname){
        String request = String.format("insert into users (login, password, nickname) values ('%s', '%s', '%s')", login,password,nickname);
        try {
            Integer resultSet = statement.executeUpdate(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized static void changeNick(String login, String newNickname){/*TODO*/
        String request = String.format("update users set nickname = '%s' where login = '%s'", newNickname, login);
        try {
            Integer resultSet = statement.executeUpdate(request);
            System.out.println("sql done");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
