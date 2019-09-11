package ru.geekbrains.java_two.lesson_a.online;

public class Parrot extends Animal {
    @Override
    void voice() {
        System.out.println("tweet");
    }

    void speak() {
        System.out.println("Polly wanna cracker!");
    }
}
