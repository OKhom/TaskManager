package ua.kiev.prog.turbosms;

import java.util.Arrays;

public class Message {
    private String[] recipients;
    private SMS sms;

    public Message(String[] recipients, SMS sms) {
        this.recipients = recipients;
        this.sms = sms;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    public SMS getSms() {
        return sms;
    }

    public void setSms(SMS sms) {
        this.sms = sms;
    }

    @Override
    public String toString() {
        return "Message{" +
                "recipients=" + Arrays.toString(recipients) +
                ", sms=" + sms +
                '}';
    }
}
