package com.boroday.ioc.testService;

public class UserService {
    private MailService mailService;

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public boolean sendEmailWithUserCount() { //boolean for test purposes
        int numberOfUsersInSystem = getUsersCount();
        mailService.sendEmail("meinEmail@gmail.com", "There are " + numberOfUsersInSystem + " users in system");
        return true;
    }

    private int getUsersCount() {
        return (int) (Math.random() * 1000);
    }
}
