package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ArrayProcessing {
    
    public int[] arrayAfterFour(int[] arr){
        boolean hasFour = false;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i<arr.length; i++){
            if (arr[i]==4){
                list = new ArrayList<>();
                hasFour = true;
            }
            else list.add(arr[i]);
        }
        if (!hasFour) throw new RuntimeException();
        return convertIntegers(list);
    }

    public static int[] convertIntegers(ArrayList<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++){
            ret[i] = integers.get(i);
        }
        return ret;
    }

    public boolean hasFourOrOne(int [] arr){
        boolean hasFour = false;
        boolean hasOne = false;
        boolean hasOtherNumbers = false;
        for (int i=0; i<arr.length; i++){
            if (arr[i] == 1) hasOne = true;
            else if (arr[i] == 4) hasFour = true;
            else hasOtherNumbers = true;
        }
        if (hasFour && hasOne && !hasOtherNumbers) return true;
        return false;
    }

    public String uniqueS(String s){
        HashMap<Character, Integer> map = new HashMap<>();
        s = s.toLowerCase();
        for (int i=0; i<s.length(); i++){
            char c=s.charAt(i);
            int x = 0;
            if (map.containsKey(c)) x = map.get(c);
            map.put(c, ++x);
        }
        for(int j=0; j<s.length(); j++){
            char c=s.charAt(j);
            if (map.get(c)==1) return Character.toString(c)+Integer.toString(j+1);
        }
        return null;
    }
    
}
