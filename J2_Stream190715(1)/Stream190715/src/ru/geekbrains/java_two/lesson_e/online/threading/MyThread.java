package ru.geekbrains.java_two.lesson_e.online.threading;

public class MyThread extends Thread {

    MyThread(String name) {
        super(name);
        start();
    }

    @Override
    public void run() {
//        while (!isInterrupted()) {
        System.out.println("I'm alive!");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("I'm done");
//        }
    }
}
