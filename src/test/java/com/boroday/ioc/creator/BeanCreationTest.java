package com.boroday.ioc.creator;

import com.boroday.ioc.context.ClassPathApplicationContext;
import com.boroday.ioc.entity.Bean;
import com.boroday.ioc.entity.BeanDefinition;
import com.boroday.ioc.exception.BeanInstantiationException;
import com.boroday.ioc.testService.MailService;
import com.boroday.ioc.testService.PaymentService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.*;

public class BeanCreationTest {
    ClassPathApplicationContext classPathApplicationContext;

    Bean beanMail;
    Bean beanPayment;
    Bean beanPaymentWithMax;

    BeanDefinition beanDefinitionPayment;
    BeanDefinition beanDefinitionPaymentInvalid;
    BeanDefinition beanDefinitionMail;
    BeanDefinition beanDefinitionPaymentWithMax;

    @Before
    public void prepareData() {
        classPathApplicationContext = new ClassPathApplicationContext();

        //beans
        beanMail = new Bean();
        beanMail.setId("mailService");
        beanMail.setValue(new MailService());

        beanPayment = new Bean();
        beanPayment.setId("paymentService");
        beanPayment.setValue(new PaymentService());

        beanPaymentWithMax = new Bean();
        beanPaymentWithMax.setId("paymentWithMaxService");
        beanPaymentWithMax.setValue(new PaymentService());

        //beanDefinitions
        beanDefinitionMail = new BeanDefinition();
        beanDefinitionMail.setId("mailService");
        beanDefinitionMail.setBeanClassName("com.boroday.ioc.testService.MailService");
        Map<String, String> valueMapMail = new HashMap<>();
        valueMapMail.put("protocol", "IMAP");
        valueMapMail.put("port", "7000");
        beanDefinitionMail.setDependencies(valueMapMail);

        beanDefinitionPayment = new BeanDefinition();
        beanDefinitionPayment.setId("paymentService");
        beanDefinitionPayment.setBeanClassName("com.boroday.ioc.testService.PaymentService");
        Map<String, String> refMapPayment = new HashMap<>();
        refMapPayment.put("mailService", "mailService");
        beanDefinitionPayment.setRefDependencies(refMapPayment);

        beanDefinitionPaymentInvalid = new BeanDefinition();
        beanDefinitionPaymentInvalid.setId("paymentService");
        beanDefinitionPaymentInvalid.setBeanClassName("com.boroday.ioc.testService.PaymentService");
        Map<String, String> refMapPaymentInvalid = new HashMap<>();
        refMapPaymentInvalid.put("notExistentService", "notExistentService");
        beanDefinitionPaymentInvalid.setRefDependencies(refMapPaymentInvalid);

        beanDefinitionPaymentWithMax = new BeanDefinition();
        beanDefinitionPaymentWithMax.setId("paymentWithMaxService");
        beanDefinitionPaymentWithMax.setBeanClassName("com.boroday.ioc.testService.PaymentService");
        Map<String, String> valueMapPaymentWithMax = new HashMap<>();
        valueMapPaymentWithMax.put("maxAmount", "7300");
        beanDefinitionPaymentWithMax.setDependencies(valueMapPaymentWithMax);
        Map<String, String> refMapPaymentWithMax = new HashMap<>();
        refMapPaymentWithMax.put("mailService", "mailService");
        beanDefinitionPaymentWithMax.setRefDependencies(refMapPaymentWithMax);
    }

    @Test
    public void testCreateBeansFromBeanDefinitions() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //prepare
        List<Bean> beansForValidation = new ArrayList<>();
        beansForValidation.add(beanMail);
        beansForValidation.add(beanPayment);
        beansForValidation.add(beanPaymentWithMax);

        List<BeanDefinition> beanDefinitions = new LinkedList<>();
        beanDefinitions.add(beanDefinitionMail);
        beanDefinitions.add(beanDefinitionPayment);
        beanDefinitions.add(beanDefinitionPaymentWithMax);

        //when
        BeanDefinitionToBeanTransformer transformer = new BeanDefinitionToBeanTransformer();
        List<Bean> beans = transformer.createBeans(beanDefinitions);

        //then
        for (Bean bean : beans) {
            assertTrue(beansForValidation.contains(bean));
        }

        assertNull(beanMail.getValue().getClass().getMethod("getProtocol").invoke(beanMail.getValue()));
        assertNull(beanPayment.getValue().getClass().getMethod("getMailService").invoke(beanPayment.getValue()));
    }

    @Test
    public void testInjectSimpleDependencies() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //prepare
        List<Bean> beansForValidation = new ArrayList<>();
        beansForValidation.add(beanMail);
        beansForValidation.add(beanPayment);
        beansForValidation.add(beanPaymentWithMax);

        List<BeanDefinition> beanDefinitions = new LinkedList<>();
        beanDefinitions.add(beanDefinitionMail);
        beanDefinitions.add(beanDefinitionPayment);
        beanDefinitions.add(beanDefinitionPaymentWithMax);

        //when
        SimpleDependencyInjector injector = new SimpleDependencyInjector();
        injector.inject(beansForValidation, beanDefinitions);

        //then
        assertEquals("IMAP", beanMail.getValue().getClass().getMethod("getProtocol").invoke(beanMail.getValue()));
        assertEquals(7000, beanMail.getValue().getClass().getMethod("getPort").invoke(beanMail.getValue()));
        assertEquals(7300, beanPaymentWithMax.getValue().getClass().getMethod("getMaxAmount").invoke(beanPaymentWithMax.getValue()));
    }

    @Test
    public void testInjectRefDependencies() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //prepare
        List<Bean> beansForValidationShort = new ArrayList<>();
        beansForValidationShort.add(beanMail);
        beansForValidationShort.add(beanPayment);

        List<BeanDefinition> beanDefinitionsShort = new LinkedList<>();
        beanDefinitionsShort.add(beanDefinitionMail);
        beanDefinitionsShort.add(beanDefinitionPayment);

        //when
        RefDependencyInjector injector = new RefDependencyInjector();
        injector.inject(beansForValidationShort, beanDefinitionsShort);

        //then
        assertEquals(MailService.class, (beanPayment.getValue().getClass().getMethod("getMailService").invoke(beanPayment.getValue())).getClass());
    }

    @Test (expected = BeanInstantiationException.class)
    public void testInjectRefDependenciesInvalidRefClass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //prepare
        List<Bean> beansForValidationShort = new ArrayList<>();
        beansForValidationShort.add(beanMail);
        beansForValidationShort.add(beanPayment);

        List<BeanDefinition> beanDefinitionsInvalid = new LinkedList<>();
        beanDefinitionsInvalid.add(beanDefinitionMail);
        beanDefinitionsInvalid.add(beanDefinitionPaymentInvalid);

        //when
        RefDependencyInjector injector = new RefDependencyInjector();
        injector.inject(beansForValidationShort, beanDefinitionsInvalid);

        //then
        beanPayment.getValue().getClass().getMethod("getMailService").invoke(beanPayment.getValue());
    }

}
