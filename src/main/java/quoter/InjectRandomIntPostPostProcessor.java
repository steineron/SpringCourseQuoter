package quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by rosteiner on 5/18/15.
 */
public class InjectRandomIntPostPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Class<?> cls = o.getClass();

        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(InjectRandomInt.class)) {
                ReflectionUtils.setField(field,
                        o,
                        getRandomIntFromAnnotation(
                                field.getDeclaredAnnotation(InjectRandomInt.class)));
            }
        }
        return o;
    }

    private int getRandomIntFromAnnotation(InjectRandomInt declaredAnnotation) {
        Random random = new Random();
        return declaredAnnotation.min() + random.nextInt(declaredAnnotation.max() - declaredAnnotation.min());
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
