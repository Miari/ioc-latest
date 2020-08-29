package com.boroday.ioc.testService;

public class DefaultManagerPromotionService implements ManagerPromotionService {
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void startAssessment() {
        System.out.println("Assessment started");
    }

    @Override
    public String promote() {
        return "Promoted to " + role;
    }
}
