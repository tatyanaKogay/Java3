package ru.geekbrains.java_two.lesson_a.online;

public class Main {
    public static void main(String[] args) {
        Cat c = new Cat();
        Snake s = new Snake();
        Parrot p = new Parrot();
        String str = "HelloWorld!";
        System.out.println(str.equals("HelloWorld"));

        Animal[] zoo = {c, s, p};
        animalMover(c, s, p, p, p);

    }

    private static void animalMover(Animal... zoo) {
        for (int i = 0; i < zoo.length; i++) {
            zoo[i].move();
            if (zoo[i] instanceof Parrot) {
//                Parrot parrot = (Parrot) zoo[i];
//                parrot.speak();
                ((Parrot) zoo[i]).speak();
            }
        }
    }

}
