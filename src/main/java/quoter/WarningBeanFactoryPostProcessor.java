package quoter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;

/**
 * Created by rosteiner on 5/18/15.
 */
public class WarningBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] names = beanFactory.getBeanDefinitionNames();
        String[] beanNamesForAnnotation = beanFactory.getBeanNamesForAnnotation(Deprecated.class);
        for (String name : beanNamesForAnnotation) {
            AbstractBeanDefinition beanDefinition = (AbstractBeanDefinition) beanFactory.getBeanDefinition(name);
            try {
                DeprecatedClass deprecated = Class.forName(beanDefinition.getBeanClassName()).getAnnotation(DeprecatedClass.class);
                Class<?> newClass = deprecated.substitute();
                beanDefinition.setBeanClassName(newClass.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (String name : names) {
            AbstractBeanDefinition beanDefinition = (AbstractBeanDefinition) beanFactory.getBeanDefinition(name);

            String destroyMethodName = beanDefinition.getDestroyMethodName(); // works for destroy-method in context.xml

            if(beanDefinition.isPrototype() && destroyMethodName!=null){
                System.err.println("WARNING - bean "+name+" is prototype but has destroy method");
            }
        }
    }
}
