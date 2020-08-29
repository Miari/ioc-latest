package com.boroday.ioc.creator;

import com.boroday.ioc.entity.Bean;
import com.boroday.ioc.entity.BeanDefinition;
import com.boroday.ioc.testService.DefaultMailService;
import com.boroday.ioc.testService.PaymentService;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BeanCreationITest {

    List<BeanDefinition> beanDefinitions;
    List<Bean> beansForValidation;
    Bean beanMail;
    Bean beanPayment;
    Bean beanPaymentWithMax;

    @Before
    public void testDataPreparation() {
        //beans
        beanMail = new Bean();
        beanMail.setId("mailService");
        beanMail.setValue(new DefaultMailService());

        beanPayment = new Bean();
        beanPayment.setId("paymentService");
        beanPayment.setValue(new PaymentService());

        beanPaymentWithMax = new Bean();
        beanPaymentWithMax.setId("paymentWithMaxService");
        beanPaymentWithMax.setValue(new PaymentService());

        //beanDefinitions
        BeanDefinition beanDefinitionMail = new BeanDefinition();
        beanDefinitionMail.setId("mailService");
        beanDefinitionMail.setBeanClassName("com.boroday.ioc.testService.DefaultMailService");
        Map<String, String> valueMapMail = new HashMap<>();
        valueMapMail.put("protocol", "IMAP");
        valueMapMail.put("port", "7000");
        beanDefinitionMail.setDependencies(valueMapMail);

        BeanDefinition beanDefinitionPayment = new BeanDefinition();
        beanDefinitionPayment.setId("paymentService");
        beanDefinitionPayment.setBeanClassName("com.boroday.ioc.testService.PaymentService");
        Map<String, String> refMapPayment = new HashMap<>();
        refMapPayment.put("mailService", "mailService");
        beanDefinitionPayment.setRefDependencies(refMapPayment);

        BeanDefinition beanDefinitionPaymentWithMax = new BeanDefinition();
        beanDefinitionPaymentWithMax.setId("paymentWithMaxService");
        beanDefinitionPaymentWithMax.setBeanClassName("com.boroday.ioc.testService.PaymentService");
        Map<String, String> valueMapPaymentWithMax = new HashMap<>();
        valueMapPaymentWithMax.put("maxAmount", "7300");
        beanDefinitionPaymentWithMax.setDependencies(valueMapPaymentWithMax);
        Map<String, String> refMapPaymentWithMax = new HashMap<>();
        refMapPaymentWithMax.put("mailService", "mailService");
        beanDefinitionPaymentWithMax.setRefDependencies(refMapPaymentWithMax);

        beansForValidation = new ArrayList<>();
        beansForValidation.add(beanMail);
        beansForValidation.add(beanPayment);
        beansForValidation.add(beanPaymentWithMax);

        beanDefinitions = new LinkedList<>();
        beanDefinitions.add(beanDefinitionMail);
        beanDefinitions.add(beanDefinitionPayment);
        beanDefinitions.add(beanDefinitionPaymentWithMax);
    }

    @Test
    public void testCreate() {
        //prepare
        BeanCreator beanCreator = new BeanCreator();

        //when
        List<Bean> beans = beanCreator.create(beanDefinitions);

        //then
        for (Bean bean : beans) {
            assertTrue(beansForValidation.contains(bean));
        }

        assertEquals("IMAP", ((DefaultMailService) beans.get(0).getValue()).getProtocol());
        assertEquals(7000, ((DefaultMailService) beans.get(0).getValue()).getPort());

        assertEquals(DefaultMailService.class, ((PaymentService) beans.get(1).getValue()).getMailService().getClass());

        assertEquals(7300, ((PaymentService) beans.get(2).getValue()).getMaxAmount());
        assertEquals(DefaultMailService.class, ((PaymentService) beans.get(2).getValue()).getMailService().getClass());
    }
}
