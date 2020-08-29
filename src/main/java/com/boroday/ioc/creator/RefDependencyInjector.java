package com.boroday.ioc.creator;

import com.boroday.ioc.entity.Bean;
import com.boroday.ioc.entity.BeanDefinition;
import com.boroday.ioc.exception.BeanInstantiationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class RefDependencyInjector {

    List<Bean> beans;

    public void inject(List<Bean> beans, List<BeanDefinition> beanDefinitions) {
        this.beans = beans;
        for (Bean bean : beans) {
            for (BeanDefinition beanDefinition : beanDefinitions) {
                if (bean.getId().equals(beanDefinition.getId())) {
                    if (beanDefinition.getRefDependencies() != null) {
                        Set<String> objectFields = beanDefinition.getRefDependencies().keySet();
                        for (String objectField : objectFields) {
                            try {
                                Object refObject = getBean(beanDefinition.getRefDependencies().get(objectField));
                                Method method;
                                Class<?> refObjectInterface = getInterfaces(refObject.getClass());
                                String methodName = "set" + Character.toUpperCase(objectField.charAt(0)) + objectField.substring(1);
                                if (refObjectInterface == null) {
                                    method = bean.getValue().getClass().getMethod(methodName, refObject.getClass());
                                } else {
                                    method = bean.getValue().getClass().getMethod(methodName, refObjectInterface);
                                }
                                method.invoke(bean.getValue(), refObject);
                            } catch (NoSuchMethodException e) {
                                throw new BeanInstantiationException("Setter for " + objectField + " field of " + bean.getValue().getClass() + " is not found", e);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new BeanInstantiationException("It is not possible to invoke setter for " + objectField + " field of " + bean.getValue().getClass(), e);
                            }
                        }
                    }
                }
            }
        }
    }

    private Class<?> getInterfaces(Class<?> clazz) {
        Class<?> refObjectInterfaces[] = clazz.getInterfaces();
        if (refObjectInterfaces.length > 0) {
            if (refObjectInterfaces.length > 1) {
                return null;
            }
            Class<?> refObjectInterface = refObjectInterfaces[0];
            return getInterfaces(refObjectInterface) == null ? refObjectInterfaces[0] : getInterfaces(refObjectInterface);
        } else {
            return null;
        }
    }

    private Object getBean(String idOfBean) {
        Object resultBean = null;
        if (!beans.isEmpty()) {
            for (Bean bean : beans) {
                if (bean.getId().equals(idOfBean)) {
                    if (resultBean == null) {
                        resultBean = bean.getValue();
                    } else {
                        throw new BeanInstantiationException("There are more than 1 Bean for id " + idOfBean);
                    }
                }
            }
            if (resultBean == null) {
                throw new BeanInstantiationException("There is no one Bean for class " + idOfBean);
            }
        } else {
            throw new BeanInstantiationException("No beans exist");
        }
        return resultBean;
    }
}
