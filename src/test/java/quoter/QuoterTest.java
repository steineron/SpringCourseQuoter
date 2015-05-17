package quoter;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by rosteiner on 5/17/15.
 */
public class QuoterTest {

    @Test
    public void testSayQuote() throws Exception {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        context.getBean("terminator",Quoter.class).sayQuote();
    }
}