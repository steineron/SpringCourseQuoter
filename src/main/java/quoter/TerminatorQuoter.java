package quoter;

import java.util.List;

/**
 * Created by rosteiner on 5/17/15.
 */
public class TerminatorQuoter implements Quoter {
    public void setQuotes(List<CharSequence> quotes) {
        this.quotes = quotes;
    }

    private List<CharSequence> quotes;

    @Benchmark
    public void sayQuote() {
        for (CharSequence quote : quotes) {
            System.out.println(quote);
        }
    }
}
