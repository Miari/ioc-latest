package com.boroday.ioc.testService;

public class SalaryService {
    private EmployeeService employeeService;
    private double salary;

    public SalaryService() {
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
