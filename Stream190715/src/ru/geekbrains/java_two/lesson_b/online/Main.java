package ru.geekbrains.java_two.lesson_b.online;

import java.io.IOException;
import java.util.Scanner;

/*

 1. Есть строка вида: "1 3 1 2\n2 3 2 2\n5 6 7 1\n3 3 1 0";
 (другими словами матрица 4x4)

 1 3 1 2
 2 3 2 2
 5 6 7 1
 3 3 1 0

 Написать метод, на вход которого подаётся такая строка,
 метод должен преобразовать строку в двумерный массив типа String[][];

 2. Преобразовать все элементы массива в числа типа int,
 просуммировать, поделить полученную сумму на 2, и вернуть результат;

 3. Ваши методы должны бросить исключения с собственными названиями в случаях:
    Если размер матрицы, полученной из строки, не равен 4x4;
    Если в одной из ячеек полученной матрицы не число; (например символ или слово)

 4. В методе main необходимо вызвать полученный метод,
 обработать возможные исключения и вывести результат расчета.
* */

public class Main {

    static void method1() {
        int a = 10;
        int b = 0;

        if (b == 0)
            throw new RuntimeException("Wrong!");

        int c = a / b;
        System.out.println(c);
    }

    public static void main(String[] args) {
//        System.out.println("Main");
//        method1();


        try (TestStream stream = new TestStream()) {
            stream.init();
            stream.open();
            stream.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
