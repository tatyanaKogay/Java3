package ru.geekbrains.java_two.lesson_a.online;

public class Snake extends Animal {
    @Override
    void move() {
        System.out.println("crawls!");
    }

    @Override
    void voice() {
        System.out.println("shshshshhhshshhh");
    }
}
