package ndpproje1;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class AverageCalculatorTest {

    public AverageCalculatorTest() {
    }

    @Test
    public void testCalculate() {
        System.out.println("calculate");
        ArrayList<Tweet> tweetsArrayList = CellPhone.getCellPhones().get(0).getcTweets();
        Calculator calculator = new Calculator(new AverageCalculator());
        float expResult = calculator.doCalculations(tweetsArrayList);
        float total = 0;
        for (Tweet t : tweetsArrayList) {
            total += t.gettValue();
        }
        float result = total / tweetsArrayList.size();
        assertEquals(result, expResult);
        fail("The test case is a prototype.");
    }

}
