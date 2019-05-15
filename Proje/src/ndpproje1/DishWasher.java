package ndpproje1;

import java.util.ArrayList;

public class DishWasher extends Product implements Comparable<DishWasher> {

    private String dUsage;   //Usage:Solo, Flush, Countertop
    private String dEnergy;   //Energy class
    private int dNumber;   //Number of programs for the dishwasher.
    private int dPrice;
    private static ArrayList<DishWasher> dishWashers = new ArrayList<DishWasher>();
    private ArrayList<Tweet> dTweets = new ArrayList<Tweet>();
    private float dTotalPolarity = 0;
    private float dAveragePolarity = 0;
    Calculator calculator;

    public DishWasher(String category, String brand, String model, String usage, String energy, int number, int price, ArrayList<Tweet> a) {
        super(category, brand, model);
        this.dUsage = usage;
        this.dEnergy = energy;
        this.dNumber = number;
        this.dPrice = price;
        this.dTweets = a;
        if (dTweets != null) {
            calculator = new Calculator(new SumCalculator());           // Using "Strategy Design Pattern"
            dTotalPolarity = calculator.doCalculations(dTweets);
            calculator = new Calculator(new AverageCalculator());
            dAveragePolarity = calculator.doCalculations(dTweets);
        }
    }

    public String getdUsage() {
        return dUsage;
    }

    public void setdUsage(String usage) {
        this.dUsage = usage;
    }

    public String getdEnergy() {
        return dEnergy;
    }

    public void setdEnergy(String energy) {
        this.dEnergy = energy;
    }

    public int getdNumber() {
        return dNumber;
    }

    public void setdNumber(int number) {
        this.dNumber = number;
    }

    public int getdPrice() {
        return dPrice;
    }

    public void setdPrice(int price) {
        this.dPrice = price;
    }

    public String showDishWasherInfo() {
        return "--" + super.getpBrand() + " " + super.getpModel() + " " + dUsage + " " + dEnergy + " Enerji Sınıfı " + dNumber + " Programlı" + dPrice + " TL-- \nbaşarıyla eklendi.";
    }

    public static ArrayList<DishWasher> getDishWashers() {
        return dishWashers;
    }

    public float getdTotalPolarity() {
        return dTotalPolarity;
    }

    @Override
    public int compareTo(DishWasher o) {
        return new Float(this.dTotalPolarity).compareTo(o.dTotalPolarity);
    }

    public float getdAveragePolarity() {
        return dAveragePolarity;
    }
}
