import java.awt.*;
import java.util.ArrayList;

public class Main<T> {
    public static void main(String[] args) {
        String[] arr = new String[5];
        for (int i = 0; i<arr.length; i++){
            arr[i] = "word"+i;
        }
        Main<String> obj1 = new Main<>();
        obj1.arrtToArrList(arr);
        obj1.exhangePositions(arr,0,3);

    }

    public void exhangePositions(T[] arr, int init, int end){
        printArr(arr);
        T temp = arr[init];
        arr[init] = arr[end];
        arr[end] = temp;
        printArr(arr);
    }

    public void printArr(T[] arr){
        for (int i = 0; i<arr.length; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    public void arrtToArrList(T[] arr){
        ArrayList<T> arrayList = new ArrayList<>();
        for (int i=0; i<arr.length; i++){
            arrayList.add(arr[i]);
        }
        System.out.println(arrayList);
    }
}
