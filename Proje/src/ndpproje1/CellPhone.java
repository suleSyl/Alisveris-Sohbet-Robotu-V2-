package ndpproje1;

import java.util.ArrayList;

public class CellPhone extends Product implements Comparable<CellPhone> {

    private float cScreen;
    private int cCameraMp;
    private int cFrontCameraMp;
    private int cStorage;
    private int cRam;
    private int cPrice;
    private static ArrayList<CellPhone> cellPhones = new ArrayList<CellPhone>();
    private ArrayList<Tweet> cTweets = new ArrayList<Tweet>();
    private float cTotalPolarity = 0;
    private float cAveragePolarity = 0;
    Calculator calculator;

    public CellPhone(String category, String brand, String model, float screen, int cameraMp, int frontCameraMp, int storage, int ram, int price, ArrayList<Tweet> a) {
        super(category, brand, model);
        this.cScreen = screen;
        this.cCameraMp = cameraMp;
        this.cFrontCameraMp = frontCameraMp;
        this.cStorage = storage;
        this.cRam = ram;
        this.cPrice = price;
        cTweets = a;
        if (cTweets != null) {
            calculator = new Calculator(new SumCalculator());         // Using "Strategy Design Pattern"
            cTotalPolarity = calculator.doCalculations(cTweets);
            calculator = new Calculator(new AverageCalculator());
            cAveragePolarity = calculator.doCalculations(cTweets);
        }
    }

    public float getcScreen() {
        return cScreen;
    }

    public void setcScreen(float screen) {
        this.cScreen = screen;
    }

    public int getcCameraMp() {
        return cCameraMp;
    }

    public void setcCameraMp(int cameraMp) {
        this.cCameraMp = cameraMp;
    }

    public int getcFrontCameraMp() {
        return cFrontCameraMp;
    }

    public void setcFrontCameraMp(int frontCameraMp) {
        this.cFrontCameraMp = frontCameraMp;
    }

    public int getcStorage() {
        return cStorage;
    }

    public void setcStorage(int storage) {
        this.cStorage = storage;
    }

    public int getcRam() {
        return cRam;
    }

    public void setcRam(int ram) {
        this.cRam = ram;
    }

    public int getcPrice() {
        return cPrice;
    }

    public void setcPrice(int price) {
        this.cPrice = price;
    }

    public String showCellPhoneInfo() {
        return "--" + super.getpBrand() + " " + super.getpModel() + " " + cScreen + " inch " + cCameraMp + " MP " + cFrontCameraMp + " MP "
                + cStorage + " GB " + cRam + " GB " + cPrice + " TL-- \nbaşarıyla eklendi.";
    }

    public static ArrayList<CellPhone> getCellPhones() {
        return cellPhones;
    }

    public ArrayList<Tweet> getcTweets() {
        return cTweets;
    }

    public float getcTotalPolarity() {
        return cTotalPolarity;
    }

    @Override
    public int compareTo(CellPhone o) {
        return new Float(this.cTotalPolarity).compareTo(o.cTotalPolarity);
    }

    public float getcAveragePolarity() {
        return cAveragePolarity;
    }

    public void setcAveragePolarity(float avg) {
        this.cAveragePolarity = avg;
    }
}
