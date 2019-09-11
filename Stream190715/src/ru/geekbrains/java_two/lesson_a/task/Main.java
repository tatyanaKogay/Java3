package ru.geekbrains.java_two.lesson_a.task;

public class Main implements MouseListener {

    private static void mousePressed(MouseListener m) {
//        m.mouseDown();
        m.mouseUp();
    }

    public static void main(String[] args) {
        class Mouse implements MouseListener {

//            @Override
//            public void mouseDown() {
//                System.out.println("mup!");
//            }

            @Override
            public void mouseUp() {
                System.out.println("mdn!");
            }
        }
        Mouse m = new Mouse();
//        m.mouseDown();
        m.mouseUp();

        mousePressed(new Mouse() {
            @Override
            public void mouseUp() {
                System.out.println("mdn!");
            }
        });

        mousePressed(
            () -> {

            }
        );

        //mousePressed(this);


//        Cat c = new Cat();
//        Siri s = new Siri();
//        Car car = new Car();
//
//        Voiceable[] arr = {c, s};
//        for (int i = 0; i < arr.length; i++) {
//            arr[i].voice();
//        }
//
//        Moveable[] arr2 = {c, car};
//        for (int i = 0; i < arr2.length; i++) {
//            arr2[i].move();
//        }
    }

    @Override
    public void mouseUp() {

    }
}
