package ndpproje1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import redis.clients.jedis.Jedis;

public class ProductFactory {

    private Jedis jedis = new Jedis("localhost");
    private String[] parsedProductInfo;
    private ArrayList<Tweet> a;
    private ArrayList<Tweet> a2;
    private List<String> list;   // 10 tweets in a list
    private SingletonSenticNet singletonSenticNet = SingletonSenticNet.GetInstance();
    private HashMap<String, String> hashMap = singletonSenticNet.getHashMap();
    private String brand, model;
    private boolean textFileOrTextBox;  // True if product is created using data retrieved from txt file
                                        // False if product is created using data typed in textfield                    

    public ProductFactory() throws IOException {  //When product is created using data retrieved from txt file

        textFileOrTextBox = true;
    }

    public ProductFactory(String brand, String model) throws IOException {  // When product is created using data typed in textfield   

        this.brand = brand;
        this.model = model;
        textFileOrTextBox = false;
    }

    public Product factoryMethod(String productType) throws IOException {

        Product product = null;
        parsedProductInfo = getparsedProductInfo();
        list = jedis.lrange(parsedProductInfo[parsedProductInfo.length - 1], 0, 9);
        a = new FileOperations().createTweetsArraylist(parsedProductInfo[parsedProductInfo.length - 1], list, hashMap);

        switch (productType) {         // Using if-else statements to provide different parameters for products that are created using data retrieved either from a txt file (To add predetermined products) or a text field (When user wants to add a product)
            case "CellPhone":
                if (textFileOrTextBox) {
                    product = new CellPhone(parsedProductInfo[parsedProductInfo.length - 1], parsedProductInfo[2], parsedProductInfo[3], Float.parseFloat(parsedProductInfo[4]), Integer.parseInt(parsedProductInfo[5]), Integer.parseInt(parsedProductInfo[6]), Integer.parseInt(parsedProductInfo[7]), Integer.parseInt(parsedProductInfo[8]), Integer.parseInt(parsedProductInfo[9]), a);
                } else {
                    product = new CellPhone("Electronic", brand, model, Float.parseFloat(parsedProductInfo[0]), Integer.parseInt(parsedProductInfo[1]), Integer.parseInt(parsedProductInfo[2]), Integer.parseInt(parsedProductInfo[3]), Integer.parseInt(parsedProductInfo[4]), Integer.parseInt(parsedProductInfo[5]), a2);
                }
                break;
            case "Laptop":
                if (textFileOrTextBox) {
                    product = new Laptop(parsedProductInfo[1], parsedProductInfo[2], parsedProductInfo[3], Float.parseFloat(parsedProductInfo[4]), parsedProductInfo[5], Integer.parseInt(parsedProductInfo[6]), Integer.parseInt(parsedProductInfo[7]), Integer.parseInt(parsedProductInfo[8]), a);
                } else {
                    product = new Laptop("Electronic", brand, model, Float.parseFloat(parsedProductInfo[0]), parsedProductInfo[1], Integer.parseInt(parsedProductInfo[2]), Integer.parseInt(parsedProductInfo[3]), Integer.parseInt(parsedProductInfo[4]), a2);
                }
                break;
            case "Printer":
                if (textFileOrTextBox) {
                    product = new Printer(parsedProductInfo[1], parsedProductInfo[2], parsedProductInfo[3], Integer.parseInt(parsedProductInfo[4]), Integer.parseInt(parsedProductInfo[5]), Integer.parseInt(parsedProductInfo[6]), a);
                } else {
                    product = new Printer("Electronic", brand, model, Integer.parseInt(parsedProductInfo[0]), Integer.parseInt(parsedProductInfo[1]), Integer.parseInt(parsedProductInfo[2]), a2);
                }
                break;
            case "Tablet":
                if (textFileOrTextBox) {
                    product = new Tablet(parsedProductInfo[1], parsedProductInfo[2], parsedProductInfo[3], Float.parseFloat(parsedProductInfo[4]), Integer.parseInt(parsedProductInfo[5]), Integer.parseInt(parsedProductInfo[6]), Integer.parseInt(parsedProductInfo[7]), Integer.parseInt(parsedProductInfo[8]), a);
                } else {
                    product = new Tablet("Electronic", brand, model, Float.parseFloat(parsedProductInfo[0]), Integer.parseInt(parsedProductInfo[1]), Integer.parseInt(parsedProductInfo[2]), Integer.parseInt(parsedProductInfo[3]), Integer.parseInt(parsedProductInfo[4]), a2);
                }
                break;
            case "Television":
                if (textFileOrTextBox) {
                    product = new Television(parsedProductInfo[1], parsedProductInfo[2], parsedProductInfo[3], Integer.parseInt(parsedProductInfo[4]), parsedProductInfo[5], Integer.parseInt(parsedProductInfo[6]), Integer.parseInt(parsedProductInfo[7]), Float.parseFloat(parsedProductInfo[8]), Integer.parseInt(parsedProductInfo[9]), a);
                }else {
                    product=new Television("Electronic", brand, model, Integer.parseInt(parsedProductInfo[0]), parsedProductInfo[1], Integer.parseInt(parsedProductInfo[2]), Integer.parseInt(parsedProductInfo[3]), Float.parseFloat(parsedProductInfo[4]), Integer.parseInt(parsedProductInfo[5]), a2);
                }
                break;
            case "Fridge":
                if (textFileOrTextBox) {
                    product = new Fridge(parsedProductInfo[1], parsedProductInfo[2], parsedProductInfo[3], parsedProductInfo[4], parsedProductInfo[5], Integer.parseInt(parsedProductInfo[6]), Integer.parseInt(parsedProductInfo[7]), a);
                }else {
                    product=new Fridge("WhiteGoods", brand, model, parsedProductInfo[0], parsedProductInfo[1], Integer.parseInt(parsedProductInfo[2]), Integer.parseInt(parsedProductInfo[3]), a2);
                }
                break;
            case "WashingMachine":
                if (textFileOrTextBox) {
                    product = new WashingMachine(parsedProductInfo[1], parsedProductInfo[2], parsedProductInfo[3], Integer.parseInt(parsedProductInfo[4]), Integer.parseInt(parsedProductInfo[5]), Integer.parseInt(parsedProductInfo[6]), a);
                }else {
                    product=new WashingMachine("WhiteGoods", brand, model, Integer.parseInt(parsedProductInfo[0]), Integer.parseInt(parsedProductInfo[1]), Integer.parseInt(parsedProductInfo[2]), a2);
                }
                break;
            case "DishWasher":
                if (textFileOrTextBox) {
                    product = new DishWasher(parsedProductInfo[1], parsedProductInfo[2], parsedProductInfo[3], parsedProductInfo[4], parsedProductInfo[5], Integer.parseInt(parsedProductInfo[6]), Integer.parseInt(parsedProductInfo[7]), a);
                }else {
                    product=new DishWasher("WhiteGoods", brand, model, parsedProductInfo[0], parsedProductInfo[1], Integer.parseInt(parsedProductInfo[2]), Integer.parseInt(parsedProductInfo[3]), a2);
                }
                break;
            case "Dryer":
                if (textFileOrTextBox) {
                    product = new Dryer(parsedProductInfo[1], parsedProductInfo[2], parsedProductInfo[3], Integer.parseInt(parsedProductInfo[4]), parsedProductInfo[5], Integer.parseInt(parsedProductInfo[6]), a);
                }else {
                    product=new Dryer("WhiteGoods", brand, model, Integer.parseInt(parsedProductInfo[0]), parsedProductInfo[1], Integer.parseInt(parsedProductInfo[2]), a2);
                }
                break;
        }
        return product;
    }
    
    public String[] getparsedProductInfo() {

        return parsedProductInfo;
    }

    public void setparsedProductInfo(String[] parsed) {

        this.parsedProductInfo = parsed;
    }

    public ArrayList<Tweet> getArrayList() {

        return a2;
    }

    public void setArrayList(ArrayList<Tweet> al) {

        a2 = al;
    }

}
