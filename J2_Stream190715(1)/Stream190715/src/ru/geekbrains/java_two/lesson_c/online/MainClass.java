package ru.geekbrains.java_two.lesson_c.online;

import java.util.*;

public class MainClass {
    /*

	 1. Создать массив с набором слов (20-30 слов, должны встречаться повторяющиеся):
	  - Написать метод, который найдёт список слов, из которых состоит текст (дубликаты не считать);
	  - Написать метод, который посчитает сколько раз встречается каждое слово;

	 2. Написать простой класс PhoneBook:
	  - В качестве ключа использовать фамилию
	  - В каждой записи всего два поля: phone, e-mail
	  - Отдельный метод для поиска номера телефона по фамилии (ввели фамилию, получили ArrayList телефонов),
	  и отдельный метод для поиска e-mail по фамилии.

	  Следует учесть, что под одной фамилией может быть несколько записей.
	  Итого должно получиться 3 класса Main, PhoneBook, Person.

    * */

    public static void main(String[] args) {
//        intList(list);
//        wrappers();
//        listCycles(list);
//        lists();
//        sets();
//        trees();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("January", 1);
        map.put("February", 2);
        map.put("March", 3);
        map.put("April", 4);
        map.put("May", 5);
        map.put("June", 6);
        System.out.println(map);
        map.put("January", 10);
        System.out.println(map.get("December"));
        System.out.println(map.containsKey("November"));
        System.out.println(map.containsValue(5));

        Set<Map.Entry<String, Integer>> set = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> e = iterator.next();
            System.out.println(e.getKey() + e.getValue());
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }

    }

    private static void trees() {
        Box b1 = new Box(1, 1);
        Box b2 = new Box(2, 2);
        Box b3 = new Box(3, 3);
        Box b4 = new Box(4, 4);
        TreeSet<Box> tree = new TreeSet<>();
        tree.add(b3);
        tree.add(b4);
        tree.add(b1);
        tree.add(b2);
        System.out.println(tree);
    }

    private static void sets() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add("January");
        set.add("February");
        set.add("March");
        set.add("April");
        System.out.println(set);
        System.out.println(set.contains("April"));
    }

    private static void lists() {
        LinkedList<Box> list = new LinkedList<>();
        Box b1 = new Box(1, 1);
        Box b2 = new Box(2, 2);
        Box b3 = new Box(3, 3);
        Box b4 = new Box(1, 1);
        list.add(b1);
        list.add(b2);
        list.add(b3);
        System.out.println(list);
        System.out.println(list.contains(b4));
        System.out.println(b1.hashCode());
        System.out.println(b4.hashCode());
        list.remove(0);
    }

    private static void intList(ArrayList<Integer> list) {
        list.add(1);
        list.add(10);
        list.add(100);
        list.add(1000);
        list.add(1001);
        list.remove(list.size() - 1);
    }

    private static void listCycles(ArrayList<Integer> list) {
        for (int j = 0; j < list.size(); j++) {
            System.out.println(list.get(j));
        }

        for (Integer ig : list) {
            System.out.println(ig);
        }

        Iterator<Integer> iter = list.iterator();
        while (iter.hasNext()) {
            int s = iter.next();
            System.out.println(s + " ");
        }
    }

    private static void wrappers() {
        int i = 0;
        Integer i0 = i;
        Float f0 = 17f;
        float f = f0;
    }
}
