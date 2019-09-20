package ru.geekbrains.java_two.lesson_c.online;

import java.util.Comparator;
import java.util.Objects;

public class Box extends Object implements Comparable {
    private int width;
    private int height;

    public Box(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("Box(%d, %d)", width, height);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Box)) return false;
        Box box = (Box) obj;
        return this.width == box.width && this.height == box.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    private int square() {
        return width * height;
    }

    @Override
    public int compareTo(Object o) {
//        if (o == this) return 0;
//        Box b = (Box) o;
//        int sq = square();
//        int boxSq = b.square();
//        if (sq < boxSq)
//            return -1;
//        else if (sq > boxSq)
//            return 1;
//        else
//            return 0;

        return (this == o) ? 0 : square() - ((Box) o).square();
    }
}
