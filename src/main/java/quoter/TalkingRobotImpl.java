package quoter;

import java.util.List;

/**
 * Created by rosteiner on 5/17/15.
 */
public class TalkingRobotImpl implements TalkingRobot {

    public void setQuoters(List<Quoter> quoters) {
        this.quoters = quoters;
    }

    List<Quoter> quoters;

    public void talk() {

        for (Quoter quoter : quoters) {
            quoter.sayQuote();
        }
    }
}
