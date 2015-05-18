package quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by rosteiner on 5/18/15.
 */
public class RunThisMethodPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Class<?> cls = o.getClass();

        Method[] classMethods = cls.getMethods();
        for (int i = 0; i < classMethods.length; i++) {
            Method method = classMethods[i];
            RunThisMethod runThisMethod = method.getDeclaredAnnotation(RunThisMethod.class);
            if (runThisMethod != null && runThisMethod.repeat() > 0) {
                int repeat = runThisMethod.repeat();
                for (int j = 0; j < repeat; j++) {
                    try {
                        method.invoke(o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return o;
    }


    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
