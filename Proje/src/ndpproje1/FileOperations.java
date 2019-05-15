package ndpproje1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import redis.clients.jedis.Jedis;

public class FileOperations {

    private String fFileName;

    public FileOperations(String fileName) {
        this.fFileName = fileName;
    }

    public FileOperations() {
    }

    public void createFileForTweets() throws IOException {  //  The method to create text files for extracted tweets using twitter4j

        String[] brands = new String[]{"GalaxyNote8", "iPhone8", "iPhoneX", "Zenfone4", "BoschDishwasher", "LGDishwasher",
            "SamsungDishwasher", "BoschDryer", "ElectroluxDryer", "HotpointDryer", "LGDryer", "BekoFridge", "LGFridge", "SamsungFridge",
            "AppleMacbook", "DellInspiron", "HPG6", "LenovoYoga", "ToshibaSatellite", "BrotherPrinter", "HPDeskjet", "SamsungPrinter",
            "XeroxPrinter", "AppleiPad", "AsusTablet", "GalaxyTab3", "LenovoYogaTab3", "LGTelevision", "PhilipsTV", "SamsungSmartTv", "SamsungWashingMachine", "LGWashingMachine", "HotpointWashingMachine", "BekoWashingMachine"};
        for (String s : brands) {
            File file = new File(s + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bWriter = new BufferedWriter(fileWriter);
            ArrayList<Tweet> arrayList = new Extraction("#" + s, 50).getTweets();
            for (Tweet t : arrayList) {
                bWriter.write(t.gettHashTag() + " " + t.gettComment() + "\n");
                bWriter.newLine();
            }
            bWriter.close();
        }
    }

    public void insertTweetsIntoRedis() throws FileNotFoundException, IOException {             // Adds tweets into Redis

        Jedis jedis = new Jedis("localhost");
        String[] brands = new String[]{"GalaxyNote8", "iPhone8", "iPhoneX", "Zenfone4", "BoschDishwasher", "LGDishwasher",
            "SamsungDishwasher", "BoschDryer", "ElectroluxDryer", "HotpointDryer", "LGDryer", "BekoFridge", "LGFridge", "SamsungFridge",
            "AppleMacbook", "DellInspiron", "HPG6", "LenovoYoga", "ToshibaSatellite", "BrotherPrinter", "HPDeskjet", "SamsungPrinter",
            "XeroxPrinter", "AppleiPad", "AsusTablet", "GalaxyTab3", "LenovoYogaTab3", "LGTelevision", "PhilipsTV", "SamsungSmartTv", "SamsungWashingMachine", "LGWashingMachine", "HotpointWashingMachine", "BekoWashingMachine"};

        for (String s : brands) {
            File file = new File("C:/Users/Hp/Desktop/Nesne Proje/NdpProje1/Tweets/" + s + ".txt");
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                jedis.lpush(s, line);
                line = reader.readLine();
            }
        }
    }    

    public void addProducts(HashMap hashmap) throws FileNotFoundException, IOException { // Adding predetermined products to the project
        
        BufferedReader reader = new BufferedReader(new FileReader("Product List.txt"));
        ProductFactory productFactory = new ProductFactory();
        String productType = null;
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            productFactory.setparsedProductInfo(line.split("\t"));
            productType = line.split("\t")[1];
            Product product = productFactory.factoryMethod(productType);         // "Using Factory Design Pattern"
            switch (productType) {
                case "CellPhone":
                    CellPhone.getCellPhones().add((CellPhone)product);
                    break;
                case "Laptop":
                    Laptop.getLaptops().add((Laptop)product);
                    break;
                case "Printer":
                    Printer.getPrinters().add((Printer)product);
                    break;
                case "Tablet":
                    Tablet.getTablets().add((Tablet)product);
                    break;
                case "Television":
                    Television.getTelevisions().add((Television)product);
                    break;
                case "Fridge":
                    Fridge.getFridges().add((Fridge)product);
                    break;
                case "WashingMachine":
                    WashingMachine.getWashingMachines().add((WashingMachine)product);
                    break;
                case "DishWasher":
                    DishWasher.getDishWashers().add((DishWasher)product);
                    break;
                case "Dryer":
                    Dryer.getDryers().add((Dryer)product);
                    break;
            }
        }
        reader.close();
    }

    public ArrayList<Tweet> createTweetsArraylist(String hashTag, List<String> list, HashMap<String, String> hm) {

        float polarity = 0;
        ArrayList<Tweet> a = new ArrayList<Tweet>();
        for (String s : list) {                       // For each tweet
            String[] parsedTweet = s.split(" ");    // Parsing each tweet
            for (String word : parsedTweet) {
                try {
                    polarity += Float.parseFloat((String) hm.get(word)); // Polarity being calculated
                } catch (Exception e) {
                    polarity = polarity;
                }
            }
            Tweet t = new Tweet(hashTag, s, polarity);
            a.add(t);
            polarity = 0;
        }
        return a;      // An ArrayList to store 10 tweets that belong to each product has been created and returned 
    }

    public String getfFileName() {
        return fFileName;
    }

    public void setfFileName(String fFileName) {
        this.fFileName = fFileName;
    }

}
