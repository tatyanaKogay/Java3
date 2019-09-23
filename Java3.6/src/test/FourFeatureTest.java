package test;

import main.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)

public class FourFeatureTest {
    ArrayProcessing arrayProcessing;
    int[] input;
    int[] result;
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][][] {
                {new Integer[]{1,8,6,4,8,5,2}, new Integer[]{8,5,2}},
                {new Integer[]{6,8,4,3,9,5,1,4,7}, new Integer[]{7}},
                {new Integer[]{4,4,4,4,4,4,4}, new Integer[]{}},
                //{new Integer[]{5,8,9,3,2}, new Integer[]{}},// проверка исключения
                {new Integer[]{4,11,1}, new Integer[]{11,1}},
        });
    }
    public FourFeatureTest(Integer[] input, Integer[] result){
        this.input = convert(input);
        this.result = convert(result);
    }

    public int[] convert(Integer[] arr){
        int [] arrRes = new int[arr.length];
        for(int i=0; i<arr.length; i++){
            arrRes[i] = arr[i];
        }
        return arrRes;
    }
    @Before
    public void init(){
        arrayProcessing = new ArrayProcessing();
    }

    @Test
    public void test1(){
        Assert.assertArrayEquals(result, arrayProcessing.arrayAfterFour(input));
    }

    @After
    public void end(){
        arrayProcessing = null;
    }
}
