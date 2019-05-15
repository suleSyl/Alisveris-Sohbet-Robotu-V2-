package ndpproje1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SingletonSenticNet {                                   // Singleton Design Pattern
    
    private static SingletonSenticNet singletonSenticNet = null;
    private HashMap<String, String> senticHashMap;

    private SingletonSenticNet() throws IOException {
        String key, value;
        senticHashMap=new HashMap<>();
        File file = new File("senticnet4.txt");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            String[] parsed = line.split("\t");
            key = parsed[0];
            value = parsed[2];
            line = reader.readLine();
            senticHashMap.put(key, value);
        }
    }

    public static SingletonSenticNet GetInstance() throws IOException {
        if (singletonSenticNet == null) {
            singletonSenticNet = new SingletonSenticNet();
        }
        return singletonSenticNet;
    }
    
    public HashMap<String, String> getHashMap() {
        return senticHashMap;
    }    
}
