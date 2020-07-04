package com.boroday.ioc.creator;

import com.boroday.ioc.entity.Bean;
import com.boroday.ioc.entity.BeanDefinition;
import com.boroday.ioc.exception.BeanInstantiationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Class.forName;

public class BeanDefinitionToBeanTransformer {
    public List<Bean> createBeans (List<BeanDefinition> beanDefinitions) {
        List<Bean> beans = new ArrayList<>();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Bean bean = new Bean();
            bean.setId(beanDefinition.getId());
            String className = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = forName(className);
                Constructor<?> constructor = clazz.getConstructor();
                Object object = constructor.newInstance();
                bean.setValue(object);
                beans.add(bean);
            } catch (ClassNotFoundException e) {
                throw new BeanInstantiationException("Class for \"" + beanDefinition.getBeanClassName() + "\" class definition from beanDefinition does not exist", e);
            } catch (NoSuchMethodException e) {
                throw new BeanInstantiationException("Constructor in class " + beanDefinition.getBeanClassName() + " does not exist", e);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new BeanInstantiationException("It is not possible to create an Instance of " + beanDefinition.getBeanClassName(), e);
            }
        }
        return beans;
    }
}
