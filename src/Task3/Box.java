package Task3;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    ArrayList<T> arrayList;

    public Box(){
        arrayList = new ArrayList<>();
    }

    public float getWeight(){
        float sum = 0;
        for (int i = 0; i<arrayList.size(); i++){
             sum += arrayList.get(i).getWeight();
        }
        return sum;
    }

    public void addFruit(T fruit){
        arrayList.add(fruit);
    }

    public boolean compare(Box<?> box){
        return (this.getWeight()-box.getWeight()) == 0;
    }

    public void replaceInto(Box<T> box){
        box.arrayList.addAll(this.arrayList);
        this.arrayList.clear();
    }
}
