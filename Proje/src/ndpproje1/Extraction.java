package ndpproje1;

import java.util.ArrayList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Extraction {             // Retrieves tweets with Twitter4j

    private ConfigurationBuilder cb;
    private Twitter twitter;
    private Query query;
    private int numberOfTweets;
    private long lastID = Long.MAX_VALUE;
    private ArrayList<Status> tweets;
    private ArrayList<Tweet> comments;
    private String eHashTag;

    public Extraction(String hashtag, int number) {
        this.cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("WJJIbooiiIk0AUVOJq3C0a46i").setOAuthConsumerSecret("XALKultvNscSj2mfuWVZa1GLkiHG33WAfO5ZeGqHg5ushYv5pj")
                .setOAuthAccessToken("924272666030366720-Q4QRzu13hb22BI7Sle2EzWcpR56Cyha").setOAuthAccessTokenSecret("9Sw5r6p6z3MKGSzjywtZrSiJALn1gXzo7TrpAF1l4PDf9");
        this.twitter = new TwitterFactory(cb.build()).getInstance();
        this.eHashTag = hashtag;
        this.query = new Query(eHashTag);
        this.tweets = new ArrayList<Status>();
        this.numberOfTweets = number;
        comments = new ArrayList<Tweet>();
    }

    public int getNumberOfTweets() {
        return numberOfTweets;
    }

    public void setNumberOfTweets(int number) {
        this.numberOfTweets = number;
    }

    public ArrayList getTweets() {
        while (tweets.size() < numberOfTweets) {
            if (numberOfTweets - tweets.size() > 100) {
                query.setCount(100);
            } else {
                query.setCount(numberOfTweets - tweets.size());
            }
            try {
                QueryResult result = twitter.search(query);
                tweets.addAll(result.getTweets());
                System.out.println("Gathered " + tweets.size() + " tweets" + "\n");
                for (Status t : tweets) {
                    if (t.getId() < lastID) {
                        lastID = t.getId();
                    }
                }
            } catch (TwitterException te) {
                System.out.println("Couldn't connect: " + te);
            }
            ;
            query.setMaxId(lastID - 1);
        }
        for (int i = 0; i < tweets.size(); i++) {
            Status t = tweets.get(i);
            String user = t.getUser().getScreenName();
            String msg = t.getText();
            System.out.println(i + " USER: " + user + t.getId() + " wrote: " + msg + "\n");
            comments.add(new Tweet(eHashTag, msg));
        }
        return comments;        // Stores retrieved tweets in arraylist and returns it
    }
}
