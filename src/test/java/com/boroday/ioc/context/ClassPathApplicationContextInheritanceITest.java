package com.boroday.ioc.context;
import com.boroday.ioc.testService.ManagerService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassPathApplicationContextInheritanceITest {
    @Test
    public void testBeanCreationWithInheritance(){
        //prepare
        ApplicationContext applicationContext = new ClassPathApplicationContext(new String[]{"src/test/resources/contextInheritance.xml"});

        //when
        ManagerService managerService =  (ManagerService) applicationContext.getBean("managerService");

        //then
        assertEquals("Promoted to Vice President", managerService.getPromotionService().promote());
    }
}
