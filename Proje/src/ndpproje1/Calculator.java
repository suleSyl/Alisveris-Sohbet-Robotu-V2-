package ndpproje1;

import java.util.ArrayList;

public class Calculator {          // Context for "Strategy Design Pattern"
    
    private ICalculation iCalculation;
    
    public Calculator(ICalculation iCalculation) {
        
        this.iCalculation=iCalculation;
    }
    
    public float doCalculations(ArrayList<Tweet> tweetsArrayList) {
        
        return iCalculation.calculate(tweetsArrayList);
    }
    
}
