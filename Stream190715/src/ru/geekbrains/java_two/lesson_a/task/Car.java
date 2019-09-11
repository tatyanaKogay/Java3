package ru.geekbrains.java_two.lesson_a.task;

public class Car implements Moveable {
    String model;

    @Override
    public void move() {
        System.out.println("Drives on wheels!");
    }
}
