package quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

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
        if(beanClass.isAnnotationPresent(Benchmark.class)){
            return Proxy.newProxyInstance(beanClass.getClassLoader(),
                    beanClass.getInterfaces(),
                    new InvocationHandler() {
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            long start = System.currentTimeMillis();
                            Object result = method.invoke(o,args);
                            long end = System.currentTimeMillis();
                            System.out.println("Benchmark: "+(end-start));
                            return result;
                        }
                    });
        }
        return o;
    }
}
