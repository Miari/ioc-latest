package com.boroday.ioc.context;

import com.boroday.ioc.exception.BeanInstantiationException;
import org.junit.Test;

public class ClassPathApplicationContextNegativeTest {
    ApplicationContext applicationContext;

    @Test(expected = BeanInstantiationException.class)
    public void createBeanSimpleDependencyNoSetter() {
        applicationContext = new ClassPathApplicationContext(new String[]{"src/test/resources/contextNoSetterMail.xml"});
    }

    @Test(expected = BeanInstantiationException.class)
    public void createBeanRefDependencyNoSetter() {
        applicationContext = new ClassPathApplicationContext(new String[]{"src/test/resources/contextNoSetterPayment.xml"});
    }
}
