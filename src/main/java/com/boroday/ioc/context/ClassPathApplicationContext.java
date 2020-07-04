package com.boroday.ioc.context;

import com.boroday.ioc.creator.BeanCreator;
import com.boroday.ioc.entity.Bean;
import com.boroday.ioc.entity.BeanDefinition;
import com.boroday.ioc.exception.BeanInstantiationException;
import com.boroday.ioc.reader.BeanDefinitionReader;
import com.boroday.ioc.reader.XMLStaxBeanDefinitionReader;

import java.util.*;

public class ClassPathApplicationContext implements ApplicationContext {
    private List<Bean> beans;
    private BeanDefinitionReader beanDefinitionReader;

    public ClassPathApplicationContext() {

    }

    public ClassPathApplicationContext(String[] path) {
        setBeanDefinitionReader(new XMLStaxBeanDefinitionReader(path));
        List<BeanDefinition> beanDefinitions = beanDefinitionReader.readBeanDefinitions();
        start(beanDefinitions);
    }

    public void setBeanDefinitionReader(BeanDefinitionReader beanDefinitionReader) {
        this.beanDefinitionReader = beanDefinitionReader;
    }

    private void start(List<BeanDefinition> beanDefinitions) {
        BeanCreator beanCreator = new BeanCreator();
        beans = beanCreator.create(beanDefinitions);
    }

    @Override
    public Object getBean(String idOfBean) {
        Object resultBean = null;
        if (!beans.isEmpty()) {
            for (Bean bean : beans) {
                if (bean.getId().equals(idOfBean)) {
                    if (resultBean == null) {
                        resultBean = bean.getValue();
                    } else {
                        throw new IllegalArgumentException("There are more than 1 Bean for id " + idOfBean);
                    }
                }
            }
            if (resultBean == null) {
                throw new IllegalArgumentException("There is no one Bean for class " + idOfBean);
            }
        } else {
            throw new BeanInstantiationException("No beans exist");
        }
        return resultBean;
    }

    @Override
    public <T> T getBean(Class<T> nameOfClass) {
        Object resultBean = null;
        if (!beans.isEmpty()) {
            for (Bean bean : beans) {
                if (bean.getValue().getClass().equals(nameOfClass)) {
                    if (resultBean == null) {
                        resultBean = bean.getValue();
                    } else {
                        throw new IllegalArgumentException("There are more than 1 Bean for class " + nameOfClass);
                    }
                }
            }
            if (resultBean == null) {
                throw new IllegalArgumentException("There is no one Bean for class " + nameOfClass);
            }
        } else {
            throw new BeanInstantiationException("No beans exist");
        }
        return (T) resultBean;
    }

    @Override
    public <T> T getBean(String idOfBean, Class<T> nameOfClass) {
        Object resultBean = null;
        if (!beans.isEmpty()) {
            for (Bean bean : beans) {
                if (bean.getValue().getClass().equals(nameOfClass) && (bean.getId().equals(idOfBean))) {
                    if (resultBean == null) {
                        resultBean = bean.getValue();
                    } else {
                        throw new IllegalArgumentException("There are more than 1 Bean for " + nameOfClass + " and id " + idOfBean);
                    }
                }
            }
            if (resultBean == null) {
                throw new IllegalArgumentException("There is no one Bean for " + nameOfClass + " and id " + idOfBean);
            }
        } else {
            throw new BeanInstantiationException("No beans exist");
        }
        return (T) resultBean;
    }

    @Override
    public List<String> getBeanNames() {
        List<String> nameOfBeans = new ArrayList<>();
        if (!beans.isEmpty()) {
            for (Bean bean : beans) {
                nameOfBeans.add(bean.getId());
            }
        } else {
            throw new BeanInstantiationException("No beans exist");
        }
        return nameOfBeans;
    }
}
