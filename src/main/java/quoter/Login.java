package quoter;

/**
 * Created by rosteiner on 5/18/15.
 */
public class Login {

    String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Login.name="+name;
    }
}
