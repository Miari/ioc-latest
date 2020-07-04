package com.boroday.ioc.context;

import java.util.List;

public interface ApplicationContext {
    Object getBean(String idOfBean);
    List<String> getBeanNames();

    <T> T getBean(Class<T> nameOfClass);
    <T> T getBean(String idOfBean, Class<T> nameOfClass);
}
