package ru.geekbrains.java_two.lesson_a.online;

public abstract class Animal {

    Animal() {

    }

    public Animal(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;

    void move() {
        System.out.println("walks!");
    }

    abstract void voice();
}
