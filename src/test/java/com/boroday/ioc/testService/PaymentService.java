package com.boroday.ioc.testService;

public class PaymentService {
    private DefaultMailService mailService;
    private int maxAmount;

    public DefaultMailService getMailService() {
        return mailService;
    }

    public void setMailService(DefaultMailService mailService) {
        this.mailService = mailService;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void pay(String from, String to, double amount) {

    }
}
