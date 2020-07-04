package com.boroday.ioc.testService;

public class EmployeeService {
    String name;
    char gender;
    int age;
    long id;
    boolean married;
    byte childNumber;
    short departmentId;
    double salary;
    float index;

    public EmployeeService() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public byte getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(byte childNumber) {
        this.childNumber = childNumber;
    }

    public short getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(short departmentId) {
        this.departmentId = departmentId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public float getIndex() {
        return index;
    }

    public void setIndex(float index) {
        this.index = index;
    }

    public double calculateMonthSalary() {
        return salary * index;
    }
}
