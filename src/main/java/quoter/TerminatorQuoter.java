package quoter;

import java.util.List;

/**
 * Created by rosteiner on 5/17/15.
 */
@Benchmark
public class TerminatorQuoter implements Quoter {
    public void setQuotes(List<String> quotes) {
        this.quotes = quotes;
    }

    private List<String> quotes;
    public void sayQuote() {
        for (String quote : quotes) {
            System.out.println(quote);
        }
    }
}
