package ndpproje1;

import java.util.ArrayList;

public class Dryer extends Product implements Comparable<Dryer> {

    private int dCapacity;   //Washing Capacity: 7 kg, 9 kg, 10 kg...   
    private String dEnergy;   //Energy Class
    private int dPrice;
    private static ArrayList<Dryer> dryers = new ArrayList<Dryer>();
    private ArrayList<Tweet> dTweets = new ArrayList<Tweet>();
    private float dTotalPolarity = 0;
    private float dAveragePolarity = 0;
    Calculator calculator;

    public Dryer(String category, String brand, String model, int capacity, String energy, int price, ArrayList<Tweet> a) {
        super(category, brand, model);
        this.dCapacity = capacity;
        this.dEnergy = energy;
        this.dPrice = price;
        this.dTweets = a;
        if (dTweets != null) {
            calculator = new Calculator(new SumCalculator());             // Using "Strategy Design Pattern"
            dTotalPolarity = calculator.doCalculations(dTweets);
            calculator = new Calculator(new AverageCalculator());
            dAveragePolarity = calculator.doCalculations(dTweets);
        }
    }

    public int getdCapacity() {
        return dCapacity;
    }

    public void setdCapacity(int capacity) {
        this.dCapacity = capacity;
    }

    public String getdEnergy() {
        return dEnergy;
    }

    public void setdEnergy(String energy) {
        this.dEnergy = energy;
    }

    public int getdPrice() {
        return dPrice;
    }

    public void setdPrice(int price) {
        this.dPrice = price;
    }

    public String showDryerInfo() {
        return "--" + super.getpBrand() + " " + super.getpModel() + " " + dCapacity + " kg " + dEnergy + " Enerji Sınıfı " + dPrice + " TL-- \nbaşarıyla eklendi.";
    }

    public static ArrayList<Dryer> getDryers() {
        return dryers;
    }

    public float getdTotalPolarity() {
        return dTotalPolarity;
    }

    @Override
    public int compareTo(Dryer o) {
        return new Float(this.dTotalPolarity).compareTo(o.dTotalPolarity);
    }

    public float getdAveragePolarity() {
        return dAveragePolarity;
    }
}
