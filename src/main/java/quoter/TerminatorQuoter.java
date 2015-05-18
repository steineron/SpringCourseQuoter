package quoter;

import javax.annotation.PreDestroy;
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

    @PreDestroy // called for singletons (!) when the context is closed
    public void terminate(){
        System.out.println("You are terminated....");
    }
}
