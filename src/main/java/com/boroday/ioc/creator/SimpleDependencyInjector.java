package com.boroday.ioc.creator;

import com.boroday.ioc.entity.Bean;
import com.boroday.ioc.entity.BeanDefinition;
import com.boroday.ioc.exception.BeanInstantiationException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class SimpleDependencyInjector {

    public void inject(List<Bean> beans, List<BeanDefinition> beanDefinitions) {
        for (Bean bean : beans) {
            for (BeanDefinition beanDefinition : beanDefinitions) {
                if (bean.getId().equals(beanDefinition.getId())) {
                    if (beanDefinition.getDependencies() != null) {
                        Set<String> objectFields = beanDefinition.getDependencies().keySet();
                        for (String objectField : objectFields) {
                            try {
                                Field field = bean.getValue().getClass().getDeclaredField(objectField);
                                Method setMethod = bean.getValue().getClass().getMethod("set" + Character.toUpperCase(objectField.charAt(0)) + objectField.substring(1), field.getType());
                                Class<?> returnedType = field.getType();
                                Object beanObject = bean.getValue();
                                if (int.class == returnedType) {
                                    setMethod.invoke(beanObject, Integer.valueOf(beanDefinition.getDependencies().get(objectField)));
                                } else if (short.class == returnedType) {
                                    setMethod.invoke(beanObject, Short.valueOf(beanDefinition.getDependencies().get(objectField)));
                                } else if (long.class == returnedType) {
                                    setMethod.invoke(beanObject, Long.valueOf(beanDefinition.getDependencies().get(objectField)));
                                } else if (float.class == returnedType) {
                                    setMethod.invoke(beanObject, Float.valueOf(beanDefinition.getDependencies().get(objectField)));
                                } else if (double.class == returnedType) {
                                    setMethod.invoke(beanObject, Double.valueOf(beanDefinition.getDependencies().get(objectField)));
                                } else if (boolean.class == returnedType) {
                                    setMethod.invoke(beanObject, Boolean.valueOf(beanDefinition.getDependencies().get(objectField)));
                                } else if (byte.class == returnedType) {
                                    setMethod.invoke(beanObject, Byte.valueOf(beanDefinition.getDependencies().get(objectField)));
                                } else if (char.class == returnedType) {
                                    setMethod.invoke(beanObject, beanDefinition.getDependencies().get(objectField).charAt(0));
                                } else if (String.class == returnedType) {
                                    setMethod.invoke(beanObject, beanDefinition.getDependencies().get(objectField));
                                } else {
                                    throw new NumberFormatException("Type of field " + objectField + " in " + bean.getValue().getClass() + " is not a primitive or a String");
                                }
                            } catch (NoSuchMethodException e) {
                                throw new BeanInstantiationException("Setter for " + objectField + " field of " + bean.getValue().getClass() + " is not found", e);
                            } catch (NoSuchFieldException e) {
                                throw new BeanInstantiationException(objectField + " field of " + bean.getValue().getClass() + " is not found", e);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new BeanInstantiationException("It is not possible to invoke setter for " + objectField + " field of " + bean.getValue().getClass(), e);
                            }
                        }
                    }
                }
            }
        }
    }
}
