package quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rosteiner on 5/18/15.
 */
public class BenchmarkAnnotationBeanPostProcessor implements BeanPostProcessor {

    Map<String, Class> mapBeanNameToClass = new HashMap<>();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass(); // here the object is 'supposed' to be the instance (not a proxy)
        Method[] methods = beanClass.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Benchmark.class)) {
                mapBeanNameToClass.put(beanName, beanClass);
                break;
            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        // this is the right place to use "AFTER" - because it does not configure the fields of the bean.

        // when post-processing objects it is better to use references saved in pre-processing call
        // because the objects bean might already be a proxy
        Class<?> beanClass = mapBeanNameToClass.get(beanName);
        return beanClass != null ?
                Proxy.newProxyInstance(beanClass.getClassLoader(),
                        beanClass.getInterfaces(),
                        new InvocationHandler() {
                            public Object invoke(Object proxy, Method interfaceMethod, Object[] args) throws Throwable {
                                Object result;
                                Method method = bean.getClass().getMethod(interfaceMethod.getName());
                                if (method.isAnnotationPresent(Benchmark.class)) {
                                    long start = System.currentTimeMillis();
                                    result = interfaceMethod.invoke(bean, args);
                                    long end = System.currentTimeMillis();
                                    System.out.println("Benchmark: " + (end - start));
                                } else {
                                    result = interfaceMethod.invoke(bean, args);
                                }
                                return result;
                            }
                        })
                :
                bean;

    }
}
