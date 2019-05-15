package ndpproje1;

import java.util.ArrayList;

public class Fridge extends Product implements Comparable<Fridge> {

    private String fType;          //Product Type:Single-door, Double-door...
    private String fEnergy;        //Energy class:A+, A++...
    private int fVolume;           //Total volume of fridge
    private int fPrice;
    private static ArrayList<Fridge> fridges = new ArrayList<Fridge>();
    private ArrayList<Tweet> fTweets = new ArrayList<Tweet>();
    private float fTotalPolarity = 0;
    private float fAveragePolarity = 0;
    Calculator calculator;

    public Fridge(String category, String brand, String model, String type, String energy, int volume, int price, ArrayList<Tweet> a) {
        super(category, brand, model);
        this.fType = type;
        this.fEnergy = energy;
        this.fVolume = volume;
        this.fPrice = price;
        this.fTweets = a;
        if (fTweets != null) {
            calculator = new Calculator(new SumCalculator());            // Using "Strategy Design Pattern"
            fTotalPolarity = calculator.doCalculations(fTweets);
            calculator = new Calculator(new AverageCalculator());
            fAveragePolarity = calculator.doCalculations(fTweets);
        }
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String type) {
        this.fType = type;
    }

    public String getfEnergy() {
        return fEnergy;
    }

    public void setfEnergy(String energy) {
        this.fEnergy = energy;
    }

    public int getfVolume() {
        return fVolume;
    }

    public void setfVolume(int volume) {
        this.fVolume = volume;
    }

    public int getfPrice() {
        return fPrice;
    }

    public void setfPrice(int price) {
        this.fPrice = price;
    }

    public String showFridgeInfo() {
        return "--" + super.getpBrand() + " " + super.getpModel() + " " + fType + " " + fEnergy + " Enerji Sınıfı " + fVolume + " lt " + fPrice + " TL-- \nbaşarıyla eklendi.";
    }

    public static ArrayList<Fridge> getFridges() {
        return fridges;
    }

    public float getfTotalPolarity() {
        return fTotalPolarity;
    }

    @Override
    public int compareTo(Fridge o) {
        return new Float(this.fTotalPolarity).compareTo(o.fTotalPolarity);
    }
    
    public float getfAveragePolarity() {
        return fAveragePolarity;
    }
}
