package test;

import main.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)


public class UniqueSymbolTest {
    ArrayProcessing arrayProcessing;
    String line;
    String answer;
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"aaaaAlaaaaaA", "l6"},
                {"iiiiiiioiiiiHR", "o8"},
                {"PyyyyAyyyyIyyyyyNyyy", "p1"},
                {"ytttttttttt", "y1"}
        });
    }
    public UniqueSymbolTest(String line, String answer){
       this.line = line;
       this.answer = answer;
    }
    @Before
    public void init(){
        arrayProcessing = new ArrayProcessing();
    }

    @Test
    public void test1(){
        Assert.assertTrue(arrayProcessing.uniqueS(line).equals(answer));
    }

    @After
    public void end(){
        arrayProcessing = null;
    }
}
