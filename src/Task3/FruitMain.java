package Task3;

public class FruitMain {
    public static void main(String[] args) {
        Box<Apple> boxA = new Box<>();
        boxA.addFruit(new Apple());
        System.out.println("weight is "+boxA.getWeight());

        Box<Apple> boxA1 = new Box<>();
        boxA1.addFruit(new Apple());
        System.out.println("weight is "+boxA1.getWeight());

        Box<Orange> boxO = new Box<>();
        boxO.addFruit(new Orange());
        System.out.println("weight is " + boxO.getWeight());

        System.out.println(boxA.compare(boxO));
        boxA.replaceInto(boxA1);
        System.out.println("weight is "+boxA.getWeight());
        System.out.println("weight is "+boxA1.getWeight());

    }
}
