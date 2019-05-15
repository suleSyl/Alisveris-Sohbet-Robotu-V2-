package ndpproje1;

import java.io.IOException;
import java.util.HashMap;

public class NdpProje1 {

    public static void main(String[] args) throws IOException {

        FileOperations fileOp = new FileOperations();
//        fileOp.createFileForTweets();         // Tweets already extracted, edited and stored in Redis before project delivery date using Twitter4j
//        fileOp.insertTweetsIntoRedis();

//        HashMap<String, String> hashmap = fileOp.createHashMap();
        
        SingletonSenticNet singletonSenticNet=SingletonSenticNet.GetInstance();
        HashMap<String,String> hashmap=singletonSenticNet.getHashMap();
        fileOp.addProducts(hashmap);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
            }
        });     
        
        
    }
}
