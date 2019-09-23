package ru.geekbrains.java_two.lesson_a.task;

public class Siri implements Voiceable {
    String owner;

    @Override
    public void voice() {
        System.out.println("Hello!");
    }
}
