package ru.geekbrains.java_two.lesson_b.online;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestStream implements Closeable {

//    TestStream() throws IOException {
//        throw new IOException();
//    }

    void init() throws IOException {
//        throw new IOException("cannot init");
        System.out.println("i init myself");
    }

    void open() throws FileNotFoundException {
        System.out.println("i open a file");
        throw new FileNotFoundException("not found");
    }

    void read() throws IOException {
        System.out.println("i read from file");
        throw new IOException("WRONG!");
    }

    public void close() throws IOException {
        System.out.println("i close myself");
        throw new IOException("Close fail");
    }
}
