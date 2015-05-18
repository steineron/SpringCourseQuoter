package quoter;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by rosteiner on 5/17/15.
 */
public class TalkingRobotImpl implements TalkingRobot {

    public void setQuoters(List<Quoter> quoters) {
        this.quoters = quoters;
    }

    List<Quoter> quoters;

    @InjectRandomInt(min=10, max=20)
    int repeatRandom;

    @PostConstruct
    @RunThisMethod(repeat = 5)
    public void talk() {

        for (Quoter quoter : quoters) {
            for (int j=0;j<repeatRandom;j++) {
                quoter.sayQuote();
            }
        }
    }
}
