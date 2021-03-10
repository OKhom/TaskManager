package ua.kiev.prog.turbosms;

public class SMS {
    private String sender;
    private String text;

    public SMS(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SMS{" +
                "sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
