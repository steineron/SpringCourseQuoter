package quoter;

/**
 * Created by rosteiner on 5/17/15.
 */
public class ShakeSpearQuoter implements Quoter {

    public void setQuote(String quote) {
        this.quote = quote;
    }

    private String quote;

    public void sayQuote() {
        System.out.println(quote);

    }
}
