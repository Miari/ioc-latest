package com.boroday.ioc.creator;

import com.boroday.ioc.entity.Bean;
import com.boroday.ioc.entity.BeanDefinition;
import java.util.List;

public class BeanCreator {
    public List<Bean> create(List<BeanDefinition> beanDefinitions) {
        BeanDefinitionToBeanTransformer transformer = new BeanDefinitionToBeanTransformer();
        List<Bean> beans = transformer.createBeans(beanDefinitions);

        SimpleDependencyInjector simpleDependencyInjector = new SimpleDependencyInjector();
        simpleDependencyInjector.inject(beans, beanDefinitions);

        RefDependencyInjector refDependencyInjector = new RefDependencyInjector();
        refDependencyInjector.inject(beans, beanDefinitions);
        return beans;
    }
}
