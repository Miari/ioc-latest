package com.boroday.ioc.testService;

public class MailService implements DefaultMailService {
    private String protocol;
    private int port;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void sendEmail(String emailTo, String content) {
        System.out.println("Sending email to " + emailTo);
        System.out.println("With content " + content);
    }
}
