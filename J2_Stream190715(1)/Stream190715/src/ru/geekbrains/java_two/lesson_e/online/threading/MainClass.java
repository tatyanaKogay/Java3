package ru.geekbrains.java_two.lesson_e.online.threading;

public class MainClass {

    private static int a = 0;
    private static int b = 0;
    private static int c = 0;

    static synchronized void incrementAllVariables() {
        for (int i = 0; i < 1000_000; i++) {
            a = a + 1;
            b = b + 1;
            c = c + 1;
        }
        String vars = String.format("a = %d, b = %d, c = %d \n", a, b, c);
        System.out.println(vars);
    }

    public static void main(String[] args) {
//        System.out.println("Hello from " + Thread.currentThread().getName());
//        MyThread t = new MyThread("MyThread");
//
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("I should be invoked after a thread!");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                incrementAllVariables();
            }
        };

        new Thread(r, "Thread #1").start();
        new Thread(r, "Thread #2").start();
        new Thread(r, "Thread #3").start();

        System.out.println("Main finished");

    }
}
