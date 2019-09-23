package test;

import main.ArrayProcessing;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CheckArrayTest {
    ArrayProcessing arrayProcessing;

    int[] input;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new Integer[]{1,1,1,1,1,4}},
                {new Integer[]{1,1,4,1,1}},
                {new Integer[]{4,1,4,4,4}},
                {new Integer[]{1,4,1,4,1,4,1}},
                {new Integer[]{4,1,4,1,1}}
        });
    }
    public CheckArrayTest(Integer[] input){
        this.input = convert(input);
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
        Assert.assertTrue(arrayProcessing.hasFourOrOne(input));
    }

    @Test
    public void test2(){
        Assert.assertFalse(arrayProcessing.hasFourOrOne(new int[]{0,0,5,9,1,4,2}));
    }

    @After
    public void end(){
        arrayProcessing = null;
    }
}
