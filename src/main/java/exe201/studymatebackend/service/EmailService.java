package exe201.studymatebackend.service;

public interface EmailService {
    public void sendHtmlEmail(String to, String subject, String text);
}
