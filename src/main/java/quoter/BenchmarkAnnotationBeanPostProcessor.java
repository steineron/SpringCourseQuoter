package quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by rosteiner on 5/18/15.
 */
public class BenchmarkAnnotationBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    public Object postProcessAfterInitialization(final Object o, String s) throws BeansException {
        // this is the right place to use "AFTER" - because it does not configure the fields of the bean.

        Class<?> beanClass = o.getClass();
        return Proxy.newProxyInstance(beanClass.getClassLoader(),
                beanClass.getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method interfaceMethod, Object[] args) throws Throwable {
                        Object result;
                        Method method = o.getClass().getMethod(interfaceMethod.getName());
                        if (method.isAnnotationPresent(Benchmark.class)) {
                            long start = System.currentTimeMillis();
                            result = interfaceMethod.invoke(o, args);
                            long end = System.currentTimeMillis();
                            System.out.println("Benchmark: " + (end - start));
                        } else {
                            result = interfaceMethod.invoke(o, args);
                        }
                        return result;
                    }
                });

    }
}
