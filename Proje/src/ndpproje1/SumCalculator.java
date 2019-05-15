package ndpproje1;

import java.util.ArrayList;

public class SumCalculator implements ICalculation {

    @Override
    public float calculate(ArrayList<Tweet> tweetsArrayList) {

        if (tweetsArrayList != null) {
            float toplam = 0;
            for (Tweet t : tweetsArrayList) {
                toplam += t.gettValue();
            }
            return toplam;
        } else {
            return 0;
        }

    }
}
