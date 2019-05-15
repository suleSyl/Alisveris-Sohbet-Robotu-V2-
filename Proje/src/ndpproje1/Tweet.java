package ndpproje1;

public class Tweet {

    private String tHashTag;
    private String tComment;
    private float tValue;

    public Tweet(String hashtag, String comment) {
        this.tHashTag = hashtag;
        this.tComment = comment;
    }

    public Tweet(String hashtag, String comment, float value) {
        this.tHashTag = hashtag;
        this.tComment = comment;
        this.tValue = value;
    }

    public String gettHashTag() {
        return tHashTag;
    }

    public void settHashTag(String hashTag) {
        this.tHashTag = hashTag;
    }

    public String gettComment() {
        return tComment;
    }

    public void settTweet(String comment) {
        this.tComment = comment;
    }

    public float gettValue() {
        return tValue;
    }

    public void settValue(float value) {
        this.tValue = value;
    }
}
