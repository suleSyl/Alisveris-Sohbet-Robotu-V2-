package ndpproje1;

import java.util.ArrayList;

public class WashingMachine extends Product implements Comparable<WashingMachine> {

    private int wCapacity;   //Washing Capacity
    private int wVolume;   //Wash Volume
    private int wPrice;
    private static ArrayList<WashingMachine> washingMachines = new ArrayList<WashingMachine>();
    private ArrayList<Tweet> wTweets = new ArrayList<Tweet>();
    private float wTotalPolarity = 0;
    private float wAveragePolarity = 0;
    Calculator calculator;

    public WashingMachine(String category, String brand, String model, int price, int capacity, int volume, ArrayList<Tweet> a) {
        super(category, brand, model);
        this.wPrice = price;
        this.wCapacity = capacity;
        this.wVolume = volume;
        this.wTweets = a;
        if (wTweets != null) {
            calculator = new Calculator(new SumCalculator());            // Using "Strategy Design Pattern"
            wTotalPolarity = calculator.doCalculations(wTweets);
            calculator = new Calculator(new AverageCalculator());
            wAveragePolarity = calculator.doCalculations(wTweets);
        }
    }

    public int getwPrice() {
        return wPrice;
    }

    public void setwPrice(int price) {
        this.wPrice = price;
    }

    public int getwCapacity() {
        return wCapacity;
    }

    public void setwCapacity(int capacity) {
        this.wCapacity = capacity;
    }

    public int getwVolume() {
        return wVolume;
    }

    public void setwVolume(int volume) {
        this.wVolume = volume;
    }

    public String showWashingMachineInfo() {
        return wPrice + " " + wCapacity + " " + wVolume + " ";
    }

    public static ArrayList<WashingMachine> getWashingMachines() {
        return washingMachines;
    }

    public float getwTotalPolarity() {
        return wTotalPolarity;
    }

    @Override
    public int compareTo(WashingMachine o) {
        return new Float(this.wTotalPolarity).compareTo(o.wTotalPolarity);
    }

    public float getwAveragePolarity() {
        return wAveragePolarity;
    }
}
