package com.boroday.ioc.creator;

import com.boroday.ioc.entity.Bean;
import com.boroday.ioc.entity.BeanDefinition;
import com.boroday.ioc.testService.EmployeeService;
import com.boroday.ioc.testService.SalaryService;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InjectSimpleDependencyAllDataTypesTest {

    Bean beanEmployee;
    Bean beanSalary;
    List<Bean> beans;
    List<Bean> beanListSalary;

    BeanDefinition beanDefinitionEmployee;
    BeanDefinition beanDefinitionSalary;
    List<BeanDefinition> beanDefinitions;
    List<BeanDefinition> beanDefinitionsListSalary;

    SimpleDependencyInjector injector;

    @Before
    public void createTestData() {
        beanEmployee = new Bean();
        beanEmployee.setId("employeeService");
        beanEmployee.setValue(new EmployeeService());
        beans = new LinkedList<>();

        beanSalary = new Bean();
        beanSalary.setId("salaryService");
        beanSalary.setValue(new SalaryService());
        beanListSalary = new LinkedList<>();

        beanDefinitionEmployee = new BeanDefinition();
        beanDefinitionEmployee.setId("employeeService");
        beanDefinitionEmployee.setBeanClassName("com.boroday.ioc.testService.EmployeeService");
        Map<String, String> valueMapEmployee = new HashMap<>();
        valueMapEmployee.put("name", "Frank");
        valueMapEmployee.put("gender", "m");
        valueMapEmployee.put("age", "33");
        valueMapEmployee.put("id", "19476699805");
        valueMapEmployee.put("married", "true");
        valueMapEmployee.put("childNumber", "3");
        valueMapEmployee.put("departmentId", "71");
        valueMapEmployee.put("salary", "1250.5");
        valueMapEmployee.put("index", "16.8F");
        beanDefinitionEmployee.setDependencies(valueMapEmployee);
        beanDefinitions = new LinkedList<>();

        beanDefinitionSalary = new BeanDefinition();
        beanDefinitionSalary.setId("salaryService");
        beanDefinitionSalary.setBeanClassName("com.boroday.ioc.testService.SalaryService");
        Map<String, String> valueMapSalary = new HashMap<>();
        valueMapSalary.put("employeeService", "EmployeeService");
        beanDefinitionSalary.setDependencies(valueMapSalary);
        beanDefinitionsListSalary = new LinkedList<>();

        injector = new SimpleDependencyInjector();
    }

    @Test
    public void injectSimpleDependency() {
        //prepare
        beans.add(beanEmployee);
        beanDefinitions.add(beanDefinitionEmployee);

        //when
        injector.inject(beans, beanDefinitions);

        //then
        assertEquals("Frank", ((EmployeeService) beans.get(0).getValue()).getName());
        assertEquals('m', ((EmployeeService) beans.get(0).getValue()).getGender());
        assertEquals(33, ((EmployeeService) beans.get(0).getValue()).getAge());
        assertEquals(19476699805L, ((EmployeeService) beans.get(0).getValue()).getId());
        assertTrue(((EmployeeService) beans.get(0).getValue()).isMarried());
        assertEquals(3, ((EmployeeService) beans.get(0).getValue()).getChildNumber());
        assertEquals(71, ((EmployeeService) beans.get(0).getValue()).getDepartmentId());
        assertEquals(1250.5, ((EmployeeService) beans.get(0).getValue()).getSalary(), 0.0);
        assertEquals(16.8F, ((EmployeeService) beans.get(0).getValue()).getIndex(), 0.0);
    }

    @Test (expected = NumberFormatException.class)
    public void createBeanException() {
        //prepare
        beanListSalary.add(beanSalary);
        beanDefinitionsListSalary.add(beanDefinitionSalary);

        //when
        injector.inject(beanListSalary, beanDefinitionsListSalary);
    }
}
