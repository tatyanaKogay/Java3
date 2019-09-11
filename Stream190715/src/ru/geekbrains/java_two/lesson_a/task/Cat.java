package ru.geekbrains.java_two.lesson_a.task;

public class Cat implements Voiceable, Moveable {
    String name;

    @Override
    public void voice() {
        System.out.println("meow!");
    }

    @Override
    public void move() {
        System.out.println("walks on paws");
    }
}
