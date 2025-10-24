package exe201.studymatebackend.service;

public interface EmailService {
    public void sendEmail(String to, String subject, String text);
}
